package esper.api4eventprocessing.repositories;

import com.espertech.esper.common.client.EPCompiled;
import esper.api4eventprocessing.models.CompiledPattern;
import esper.api4eventprocessing.models.CompiledEventType;

import java.util.ArrayList;
import java.util.List;

public class CompiledEPLRepository {
    private List<CompiledEventType> compiledEventTypes;
    private List<CompiledPattern> compiledPatterns;

    public CompiledEPLRepository() {
        this.compiledEventTypes = new ArrayList<>();
        this.compiledPatterns = new ArrayList<>();
    }

    public EPCompiled findCompiledEventType(String eventTypeName) {
        for (CompiledEventType schema : this.compiledEventTypes) {
            if (eventTypeName.equals(schema.name)) {
                return schema.epCompiled;
            }
        }
        return null;
    }

    public EPCompiled findCompiledPattern(String patternName) {
        for (CompiledPattern pattern : this.compiledPatterns) {
            if (patternName.equals(pattern.name)) {
                return pattern.epCompiled;
            }
        }
        return null;
    }

    public void removeCompiledEventType(String eventTypeName) {
        for (CompiledEventType schema : this.compiledEventTypes) {
            if (eventTypeName.equals(schema.name)) {
                this.compiledEventTypes.remove(schema);
                break;
            }
        }
    }

    public void removeCompiledPattern(String patternName) {
        for (CompiledPattern pattern : this.compiledPatterns) {
            if (patternName.equals(pattern.name)) {
                this.compiledPatterns.remove(pattern);
                break;
            }
        }
    }

    public void addCompiledEventType(String eventTypeName, EPCompiled compiled){
        compiledEventTypes.add(new CompiledEventType(eventTypeName,compiled));
    }

    public void addCompiledPattern(String patternName, EPCompiled compiled){
        compiledEventTypes.add(new CompiledEventType(patternName,compiled));
    }

    public boolean isEventTypeCompiled(String eventTypeName){
        for (CompiledEventType eventType : this.compiledEventTypes) {
            if (eventTypeName.equals(eventType.name)) {
               return true;
            }
        }
        return false;
    }

    public boolean isPatternCompiled(String patternName){
        for (CompiledPattern pattern : this.compiledPatterns) {
            if (patternName.equals(pattern.name)){
                return true;
            }
        }
        return false;
    }
}
