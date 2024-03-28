package esper.api4eventprocessing.services;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPUndeployException;
import esper.api4eventprocessing.models.CompiledPattern;
import esper.api4eventprocessing.petitions.EventJsonPetition;
import esper.api4eventprocessing.petitions.PatternPetition;
import esper.api4eventprocessing.repositories.CompiledEPLRepository;
import esper.api4eventprocessing.repositories.EsperEngineRepository;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.responses.EventTypeResponse;
import org.springframework.stereotype.Service;

@Service
public class EsperService {
    private EsperEngineRepository esperEngineRepository;
    private CompiledEPLRepository compiledEPLRepository;



    public EsperService() {
        this.esperEngineRepository = EsperEngineRepository.getInstance();
        this.compiledEPLRepository = new CompiledEPLRepository();
    }

    public EventTypeResponse newEventTypeJson(EventTypePetition newEventType) throws EPCompileException {
        if(!this.compiledEPLRepository.isEventTypeCompiled(newEventType.name)){
            EPCompiled epCompiled = this.esperEngineRepository.compile(newEventType.schema);
            this.compiledEPLRepository.addCompiledEventType(newEventType.name,epCompiled);
            return new EventTypeResponse(newEventType.name, newEventType.schema, "Event type "+ newEventType.name + " is ready to deploy");
        }
        return new EventTypeResponse(newEventType.name, newEventType.schema, "Event type "+ newEventType.name + " is already compiled");
    }

    public String deployEventType(String eventTypeName) throws EPDeployException {
        EPCompiled epCompiled = this.compiledEPLRepository.findCompiledEventType(eventTypeName);
        this.compiledEPLRepository.removeCompiledEventType(eventTypeName);
        return this.esperEngineRepository.deploy(epCompiled);
    }

    public String[] getDeployedEventTypes(){
        return this.esperEngineRepository.getDeployedEventTypes();
    }


    public boolean isDeployed(String id){
        return this.esperEngineRepository.isDeployed(id);
    }

    public String undeploy(String id){
        try {
            return this.esperEngineRepository.undeploy(id);
        } catch (EPUndeployException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendEventJson(EventJsonPetition eventJsonPetition){
        return this.esperEngineRepository.sendEventJson(eventJsonPetition.eventTypeName, eventJsonPetition.content);
    }

    public String addNewPattern(PatternPetition patternPetition) throws EPCompileException {
        EPCompiled epCompiled = this.esperEngineRepository.compile(patternPetition.query);
        this.compiledPatterns.add(new CompiledPattern(patternPetition.name, epCompiled));
        return "Pattern compiled successfully";
    }

    public String deployPattern(String name) throws EPDeployException {
        EPCompiled epCompiled = this.findCompiledPattern(name);
        this.removeCompiledPattern(name);
        return  this.esperEngineRepository.deploy(epCompiled);
    }

    public void addListener(String stamentName, String id){
       this.esperEngineRepository.addListener(stamentName,id);
    }


}
