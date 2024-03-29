package esper.api4eventprocessing.interfaces;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPDeployment;
import esper.api4eventprocessing.models.EventTypeDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface IEventTypeOperations {
    public boolean isEventTypeCompiled(String eventTypeName);
    public void addCompiledEventType(String eventTypeName, String schema,EPCompiled compiled);
    public EPCompiled findCompiledEventType(String eventTypeName);
    public void addDeployedEventType(String eventTypeName, EPDeployment deployment);
    public String getEventTypeSchema(String eventTypeName);
    public List<String> getDeployedEventTypesWithName();
    public String removeDeployedId(String deployId);
}
