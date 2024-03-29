package esper.api4eventprocessing.responses;

public class EventTypeResponse {
    private String name;
    private String schema;
    private String message;

    public EventTypeResponse(String eventTypeName,String schema,String message){
        this.name = eventTypeName;
        this.schema = schema;
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
