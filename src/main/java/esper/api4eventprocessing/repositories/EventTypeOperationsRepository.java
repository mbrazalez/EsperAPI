package esper.api4eventprocessing.repositories;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPDeployment;
import esper.api4eventprocessing.interfaces.IEventTypeOperations;
import esper.api4eventprocessing.models.EventTypeDetails;
import java.util.*;

public class EventTypeOperationsRepository implements IEventTypeOperations {
    private Map<String, EventTypeDetails> eventTypeDetailsMap;

    public EventTypeOperationsRepository() {
        this.eventTypeDetailsMap = new HashMap<>();
    }

    public boolean isEventTypeCompiled(String eventTypeName){
        return eventTypeDetailsMap.containsKey(eventTypeName);
    }

    public void addCompiledEventType(String eventTypeName, String schema,EPCompiled compiled){
        EventTypeDetails newEventType = new EventTypeDetails(eventTypeName, schema, compiled);
        eventTypeDetailsMap.put(eventTypeName, newEventType);
    }

    public EPCompiled findCompiledEventType(String eventTypeName) {
        EventTypeDetails details = eventTypeDetailsMap.get(eventTypeName);
        return details != null ? details.getEpCompiled() : null;
    }

    public void addDeployedEventType(String eventTypeName, EPDeployment deployment){
        EventTypeDetails eventTypeDetails = eventTypeDetailsMap.get(eventTypeName);
        eventTypeDetails.setEpDeployment(deployment);
    }

    public String getEventTypeSchema(String eventTypeName){
        EventTypeDetails eventTypeDetails = eventTypeDetailsMap.get(eventTypeName);
        return eventTypeDetails.getSchema();
    }

    public List<String> getDeployedEventTypesWithName(){
        List<String> deployedEventTypesWithName = new ArrayList<>();
        Collection<EventTypeDetails> eventTypes = this.eventTypeDetailsMap.values();

        for (EventTypeDetails eventType :  eventTypes){
            if (eventType.getDeployId() != null){
                deployedEventTypesWithName.add(eventType.getName() + ": " + eventType.getDeployId());
            }
        }

        return deployedEventTypesWithName;
    }

    public String removeDeployedId(String deployId){
        Collection<EventTypeDetails> eventTypes = this.eventTypeDetailsMap.values();

        for (EventTypeDetails eventType :  eventTypes){
            if (eventType.getDeployId().equals(deployId)){
                eventType.setDeployId(null);
                return eventType.getName();
            }
        }

        return null;
    }
}