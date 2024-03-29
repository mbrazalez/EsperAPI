package esper.api4eventprocessing.models;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPDeployment;

public class PatternDetails {
    private String name;
    private String deployId;
    private EPCompiled epCompiled;
    private EPDeployment epDeployment;

    public PatternDetails(String name, EPCompiled epCompiled){
        this.name = name;
        this.epCompiled = epCompiled;
    }

    public String getName() {
        return name;
    }

    public String getDeployId() {
        return deployId;
    }

    public EPCompiled getEpCompiled() {
        return epCompiled;
    }

}
