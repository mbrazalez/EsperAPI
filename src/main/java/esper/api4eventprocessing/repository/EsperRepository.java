package esper.api4eventprocessing.repository;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.util.NameAccessModifier;
import com.espertech.esper.compiler.client.*;
import com.espertech.esper.runtime.client.*;
import esper.api4eventprocessing.events.PM10Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class EsperRepository {
    private static final Logger log = LoggerFactory.getLogger(EsperRepository.class);

    private static EsperRepository instance;
    private Configuration config;
    private EPRuntime runtime;

    private EsperRepository() {
        initializeConfiguration();
    }

    public static synchronized EsperRepository getInstance() {
        if (instance == null) {
            instance = new EsperRepository();
        }
        return instance;
    }

    private void initializeConfiguration() {
        config = new Configuration();
    }

    public EPRuntime getRuntime() {
        return runtime;
    }

    public synchronized String addEventType(String eventName, String eventType) {
        try {
            config.getCommon().addEventType(eventName, eventType);
            runtime = EPRuntimeProvider.getDefaultRuntime(config);
            return "Event type added successfully.";
        } catch (Exception e) {
            log.error("Error adding event type: " + e.getMessage(), e);
            return "Error adding event type.";
        }
    }

    public String deployPattern(String epl) {
        CompilerArguments args = new CompilerArguments(config);
        args.getPath().add(runtime.getRuntimePath());

        try {
            EPCompiled compiled = EPCompilerProvider.getCompiler().compile(epl, args);;
            DeploymentOptions deploymentOptions = new DeploymentOptions();
            EPDeployment deployment = runtime.getDeploymentService().deploy(compiled,deploymentOptions);
            EPStatement statement = deployment.getStatements()[0];
            String stamentId = statement.getDeploymentId();
            statement.addListener((newEvents, oldEvents, epStatement, epRuntime) -> {
                if (newEvents != null) {
                    int r = new Random().nextInt(10);
                    System.out.printf("algo complejo hay" + r);

                }
            });
            return stamentId;

        } catch (EPCompileException | EPDeployException e) {
            log.error("Error deploying pattern: " + e.getMessage(), e);
            return null;
        }
    }
}
