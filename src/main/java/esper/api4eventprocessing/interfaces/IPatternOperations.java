package esper.api4eventprocessing.interfaces;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.runtime.client.EPDeployment;

import java.util.List;

public interface IPatternOperations {
    public boolean isPatternCompiled(String patternName);
    public void addCompiledPattern(String patternName, String query, EPCompiled compiled);
    public EPCompiled findCompiledPattern(String patterName);
    public void addDeployedPattern(String patternName, EPDeployment deployment);
    public String getPatternQuery(String patternName);
    public List<String> getDeployedPatternsWithName();
    public String removeDeployedId(String deployId);
}
