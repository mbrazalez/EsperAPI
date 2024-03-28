package esper.api4eventprocessing.models;

import com.espertech.esper.common.client.EPCompiled;

public class CompiledEventType {
    public CompiledEventType(String name, EPCompiled epCompiled){
        this.name = name;
        this.epCompiled = epCompiled;
    }

    public String name;
    public EPCompiled epCompiled;
}
