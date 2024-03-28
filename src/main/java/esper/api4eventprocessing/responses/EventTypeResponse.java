package esper.api4eventprocessing.responses;

import lombok.Data;

@Data
public class EventTypeResponse {
    private String eventTypeName;
    private String schema;
    private String message;

    public EventTypeResponse(String eventTypeName,String schema,String message){
        this.eventTypeName = eventTypeName;
        this.schema = schema;
        this.message = message;
    }
}
