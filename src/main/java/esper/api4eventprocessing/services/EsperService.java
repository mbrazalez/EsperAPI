package esper.api4eventprocessing.services;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPUndeployException;
import esper.api4eventprocessing.petitions.EventJsonPetition;
import esper.api4eventprocessing.petitions.PatternPetition;
import esper.api4eventprocessing.repositories.EPLMonitorRepository;
import esper.api4eventprocessing.repositories.EsperEngineRepository;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.responses.EventTypeResponse;
import org.springframework.stereotype.Service;

@Service
public class EsperService {
    private EsperEngineRepository esperEngineRepository;
    private EPLMonitorRepository eplMonitorRepository;

    public EsperService() {
        this.esperEngineRepository = EsperEngineRepository.getInstance();
        this.eplMonitorRepository = new EPLMonitorRepository();
    }

    public EventTypeResponse newEventTypeJson(EventTypePetition newEventType) throws EPCompileException {
        String eventTypeName = newEventType.name;
        String schema = newEventType.schema;
        boolean eventAlreadyCompiled = this.eplMonitorRepository.isEventTypeCompiled(eventTypeName);

        if(!eventAlreadyCompiled){
            EPCompiled epCompiled = this.esperEngineRepository.compile(schema);
            this.eplMonitorRepository.addCompiledEventType(eventTypeName, schema, epCompiled);
            return new EventTypeResponse(eventTypeName, schema, "Event type compiled successfully");
        }

        return null;
    }

    public EventTypeResponse deployEventType(String eventTypeName) throws EPDeployException {
        EPCompiled epCompiled = this.eplMonitorRepository.findCompiledEventType(eventTypeName);

        if (epCompiled != null){
            EPDeployment epDeployment = this.esperEngineRepository.deploy(epCompiled);
            this.eplMonitorRepository.addDeployedEventType(eventTypeName, epDeployment);
            String schema = this.eplMonitorRepository.getEventTypeSchema(eventTypeName);
            return new EventTypeResponse(eventTypeName, schema, "Event type deployed successfully with id " + epDeployment.getDeploymentId());
        }

        return null;
    }

    public String[] getDeployedEventTypes(){
        String[] deployedEventTypes = this.esperEngineRepository.getDeployedEventTypes();

        if (deployedEventTypes.length > 0){
           return this.eplMonitorRepository.getDeployedEventTypesWithName(deployedEventTypes.length);
        }

        return  deployedEventTypes;
    }


    public boolean isDeployed(String id){
        return this.esperEngineRepository.isDeployed(id);
    }

    public String undeploy(String id) {
        try {
            String deployId = this.esperEngineRepository.undeploy(id);
            String eventTypeName = this.eplMonitorRepository.removeDeployedId(deployId);
            return eventTypeName;

        } catch (EPUndeployException e) {
            return null;
        }
    }

    public String sendEventJson(EventJsonPetition eventJsonPetition){
        return this.esperEngineRepository.sendEventJson(eventJsonPetition.eventTypeName, eventJsonPetition.content);
    }

    public String addNewPattern(PatternPetition patternPetition) throws EPCompileException {
        EPCompiled epCompiled = this.esperEngineRepository.compile(patternPetition.query);
        this.eplMonitorRepository.addCompiledEventType(patternPetition.name, patternPetition.query,epCompiled);
        return "Pattern compiled successfully";
    }

    public String deployPattern(String name) throws EPDeployException {
        EPCompiled epCompiled = this.eplMonitorRepository.findCompiledPattern(name);
        return  "this.esperEngineRepository.deploy(epCompiled)";
    }

    public void addListener(String stamentName, String id){
       this.esperEngineRepository.addListener(stamentName,id);
    }


}
