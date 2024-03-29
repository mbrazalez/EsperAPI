package esper.api4eventprocessing.repositories;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.*;
import com.espertech.esper.runtime.client.*;
import esper.api4eventprocessing.events.HumidityEvent;
import esper.api4eventprocessing.events.PM10Event;
import esper.api4eventprocessing.events.PM25Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.WindowEvent;
import java.util.*;

public class EsperEngineRepository {
    private static final Logger log = LoggerFactory.getLogger(EsperEngineRepository.class);
    private static EsperEngineRepository instance;
    private Configuration configuration;
    private CompilerArguments compilerArguments;
    private EPCompiler epCompiler;
    private EPRuntime epRuntime;

    private EsperEngineRepository() {
        initializeConfiguration();
    }

    public static synchronized EsperEngineRepository getInstance() {
        if (instance == null) {
            instance = new EsperEngineRepository();
        }
        return instance;
    }

    private void initializeConfiguration() {
        this.configuration = new Configuration();
        //this.configuration.getCompiler().getByteCode().setAccessModifierEventType(NameAccessModifier.PUBLIC);
        //this.configuration.getCompiler().getByteCode().setBusModifierEventType(EventTypeBusModifier.BUS);
        this.initializePollutionControlEventTypes();
        this.compilerArguments = new CompilerArguments(this.configuration);
        this.epCompiler = EPCompilerProvider.getCompiler();
        this.epRuntime = EPRuntimeProvider.getDefaultRuntime(this.configuration);
        System.out.printf("Esper Singleton Engine is ready! \n");
    }

    private void initializePollutionControlEventTypes(){
        this.configuration.getCommon().addEventType(HumidityEvent.class);
        this.configuration.getCommon().addEventType(PM10Event.class);
        this.configuration.getCommon().addEventType(WindowEvent.class);
        this.configuration.getCommon().addEventType(PM25Event.class);
    }

    public EPCompiled compile(String epl) throws EPCompileException {
        this.compilerArguments.getPath().add(this.epRuntime.getRuntimePath());
        return epCompiler.compile(epl, this.compilerArguments);
    }

    public EPDeployment deploy(EPCompiled epCompiled) throws EPDeployException {
        return this.epRuntime.getDeploymentService().deploy(epCompiled);
    }

    public String[] getDeployedEventTypes(){
        return epRuntime.getDeploymentService().getDeployments();
    }

    public boolean isDeployed(String id){
        return epRuntime.getDeploymentService().isDeployed(id);
    }

    public String undeploy(String deploymentId) throws EPUndeployException {
        epRuntime.getDeploymentService().undeploy(deploymentId);
        return deploymentId;
    }

    public void sendEvent(Map<String, Object> event, String name) {
        epRuntime.getEventService().sendEventMap(event, name);
    }

    public String sendEventJson(String name, String event){
        epRuntime.getEventService().sendEventJson(event,name);
        return "event sended";
    }

    public EPStatement getStatement(String deploymentId, String statementName) {
        return epRuntime.getDeploymentService().getStatement(deploymentId, statementName);
    }

    public void addListener(String name, String id){
        EPStatement deployedStmnt = this.epRuntime.getDeploymentService().getStatement(id,name);
        deployedStmnt.addListener((newEvent,oldEvent, stmnt, runtime) -> {
            for (EventBean event: newEvent){
                System.out.printf("Evento complejo detectado, venga a funcionar piolin");
            }
        });
    }

}
