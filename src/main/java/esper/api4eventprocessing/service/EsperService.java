package esper.api4eventprocessing.service;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPUndeployException;
import esper.api4eventprocessing.models.CompiledSchema;
import esper.api4eventprocessing.repository.EsperRepository;
import esper.api4eventprocessing.petitions.EventTypePetition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsperService {
    private EsperRepository esperRepository;
    private List<CompiledSchema> compiledSchemas;


    public EsperService() {
        this.esperRepository = EsperRepository.getInstance();
        this.compiledSchemas = new ArrayList<>();
    }

    public String newEventTypeJson(EventTypePetition newEventType){
        System.out.printf("@Name('" + newEventType.name +"') create json schema " + newEventType.content);
        EPCompiled epCompiled = this.esperRepository.compile("@Name('" + newEventType.name +"') create json schema " + newEventType.content);
        this.compiledSchemas.add(new CompiledSchema(newEventType.name, epCompiled));
        return ("Event type " + newEventType.content + " compiled sucessfully");
    }

    public String deployEventType(String eventTypeName){
        EPCompiled epCompiled = findCompiledEvent(eventTypeName);
        return this.esperRepository.deploy(epCompiled);
    }

    public String[] getDeployedEventTypes(){
        return this.esperRepository.getDeployedEventTypes();
    }

    private EPCompiled findCompiledEvent(String eventTypeName) {
        for (CompiledSchema schema : this.compiledSchemas) {
            if (eventTypeName.equals(schema.name)) {
                return schema.epCompiled;
            }
        }
        return null;
    }

    public boolean isDeployed(String id){
        return this.esperRepository.isDeployed(id);
    }

    public String undeployEventType(String id){
        try {
            return this.esperRepository.undeploy(id);
        } catch (EPUndeployException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendEventJson(String name, String event){
        return this.esperRepository.sendEventJson(name, event);
    }


}
