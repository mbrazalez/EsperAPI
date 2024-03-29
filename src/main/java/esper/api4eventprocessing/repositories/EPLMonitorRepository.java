package esper.api4eventprocessing.repositories;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPDeployment;
import esper.api4eventprocessing.models.EventTypeDetails;
import esper.api4eventprocessing.models.PatternDetails;

import java.util.*;

public class EPLMonitorRepository {
    private Map<String, EventTypeDetails> eventTypeDetailsMap;
    private Map<String, PatternDetails> patternDetailsMap;

    public EPLMonitorRepository() {
        this.eventTypeDetailsMap = new HashMap<>();
        this.patternDetailsMap = new HashMap<>();
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

    public String[] getDeployedEventTypesWithName(int size){
        List<String> deployedEventTypesWithName = new ArrayList<>();
        Collection<EventTypeDetails> eventTypes = this.eventTypeDetailsMap.values();

        for (EventTypeDetails eventType :  eventTypes){
            if (eventType.getDeployId() != null){
                deployedEventTypesWithName.add(eventType.getName() + ": " + eventType.getDeployId());
            }
        }

        return deployedEventTypesWithName.toArray(new String[size]);
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


    public EPCompiled findCompiledPattern(String patternName) {
        PatternDetails details = patternDetailsMap.get(patternName);
        return details != null ? details.getEpCompiled() : null;
    }

    public void removeCompiledEventType(String eventTypeName) {
        eventTypeDetailsMap.remove(eventTypeName);
    }

    public void removeCompiledPattern(String patternName) {
        patternDetailsMap.remove(patternName);
    }

    public boolean isPatternCompiled(String patternName){
        return patternDetailsMap.containsKey(patternName);
    }
}
