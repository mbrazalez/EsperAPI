package esper.api4eventprocessing.petitions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventTypePetition {
    @JsonProperty("name")
    public String name;
    @JsonProperty("schema")
    public String schema;
}
