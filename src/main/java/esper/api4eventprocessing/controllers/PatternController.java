package esper.api4eventprocessing.controllers;

import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import esper.api4eventprocessing.petitions.PatternPetition;
import esper.api4eventprocessing.services.EsperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatternController {

    @Autowired
    private EsperService esperService;

    @PostMapping("/new_pattern")
    public String addNewPattern(@RequestBody PatternPetition patternPetition){
        try {
            return esperService.addNewPattern(patternPetition);
        } catch (EPCompileException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/deploy_pattern/{name}")
    public String deployPattern(@PathVariable("name") String name){
        String id = null;
        try {
            id = esperService.deployPattern(name);
        } catch (EPDeployException e) {
            throw new RuntimeException(e);
        }
        esperService.addListener(name,id);
        return "statement deployed";
    }

    @DeleteMapping("/undeploy_pattern/{id}")
    public String undeployPattern(@PathVariable("id")String id){
        return esperService.undeploy(id);
    }

    @GetMapping("/pattern_is_deployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        return esperService.isDeployed(id);
    }

}
