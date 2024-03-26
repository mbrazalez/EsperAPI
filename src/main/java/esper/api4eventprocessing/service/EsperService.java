package esper.api4eventprocessing.service;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPUndeployException;
import esper.api4eventprocessing.models.CompiledPattern;
import esper.api4eventprocessing.models.CompiledSchema;
import esper.api4eventprocessing.petitions.EventPetition;
import esper.api4eventprocessing.petitions.PatternPetition;
import esper.api4eventprocessing.repository.EsperRepository;
import esper.api4eventprocessing.petitions.EventTypePetition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsperService {
    private EsperRepository esperRepository;
    private List<CompiledSchema> compiledEventTypes;
    private List<CompiledPattern> compiledPatterns;

    public EsperService() {
        this.esperRepository = EsperRepository.getInstance();
        this.compiledEventTypes = new ArrayList<>();
        this.compiledPatterns = new ArrayList<>();
    }

    public String newEventTypeJson(EventTypePetition newEventType){
        EPCompiled epCompiled = this.esperRepository.compile("@Name('" + newEventType.name +"') create json schema " + newEventType.schema);
        this.compiledEventTypes.add(new CompiledSchema(newEventType.name, epCompiled));
        return ("Event type " + newEventType.schema + " compiled sucessfully");
    }

    public String deployEventType(String eventTypeName){
        EPCompiled epCompiled = this.findCompiledEventType(eventTypeName);
        this.removeCompiledEventType(eventTypeName);
        return this.esperRepository.deploy(epCompiled);
    }

    public String[] getDeployedEventTypes(){
        return this.esperRepository.getDeployedEventTypes();
    }


    public boolean isDeployed(String id){
        return this.esperRepository.isDeployed(id);
    }

    public String undeploy(String id){
        try {
            return this.esperRepository.undeploy(id);
        } catch (EPUndeployException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendEventJson(EventPetition eventPetition){
        return this.esperRepository.sendEventJson(eventPetition.eventTypeName, eventPetition.content);
    }

    public String addNewPattern(PatternPetition patternPetition){
        EPCompiled epCompiled = this.esperRepository.compile("@Name('" + patternPetition.name +"') " + patternPetition.query);
        this.compiledPatterns.add(new CompiledPattern(patternPetition.name, epCompiled));
        return "Pattern compiled successfully";
    }

    public String deployPattern(String name){
        EPCompiled epCompiled = this.findCompiledPattern(name);
        this.removeCompiledPattern(name);
        this.esperRepository.deploy(epCompiled);
        return "Pattern deployed successfully";
    }


    private EPCompiled findCompiledEventType(String eventTypeName) {
        for (CompiledSchema schema : this.compiledEventTypes) {
            if (eventTypeName.equals(schema.name)) {
                return schema.epCompiled;
            }
        }
        return null;
    }

    private EPCompiled findCompiledPattern(String patternName) {
        for (CompiledPattern pattern : this.compiledPatterns) {
            if (patternName.equals(pattern.name)) {
                return pattern.epCompiled;
            }
        }
        return null;
    }

    private void removeCompiledEventType(String eventTypeName) {
        for (CompiledSchema schema : this.compiledEventTypes) {
            if (eventTypeName.equals(schema.name)) {
               this.compiledEventTypes.remove(schema);
               break;
            }
        }
    }

    private void removeCompiledPattern(String patternName) {
        for (CompiledPattern pattern : this.compiledPatterns) {
            if (patternName.equals(pattern.name)) {
                this.compiledPatterns.remove(pattern);
                break;
            }
        }
    }
}
