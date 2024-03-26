package esper.api4eventprocessing.petitions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatternPetition {
    @JsonProperty("name")
    public String name;
    @JsonProperty("query")
    public String query;
}
