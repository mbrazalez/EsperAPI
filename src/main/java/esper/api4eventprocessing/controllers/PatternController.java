package esper.api4eventprocessing.controllers;

import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import esper.api4eventprocessing.petitions.PatternPetition;
import esper.api4eventprocessing.responses.EventTypeResponse;
import esper.api4eventprocessing.responses.PatternResponse;
import esper.api4eventprocessing.services.EsperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatternController {

    private final EsperService esperService;

    public PatternController(EsperService esperService) {
        this.esperService = esperService;
    }

    @PostMapping("/new_pattern")
    public ResponseEntity<?> addNewPattern(@RequestBody PatternPetition patternPetition){
        String name = patternPetition.name;
        String query = patternPetition.query;

        if (patternPetition.name == null || patternPetition.query == null)
            return new ResponseEntity<>("Parameters missing in the request body", HttpStatus.BAD_REQUEST);

        try {
            PatternResponse response = esperService.addNewPattern(name,query);

            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            response = new PatternResponse(name, query, "This pattern has been already compiled");
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);

        } catch (EPCompileException e) {
            return new ResponseEntity<>("Failed to compile pattern due to syntax or other esper compilation errors." , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deploy_pattern/{name}")
    public ResponseEntity<?> deployPattern(@PathVariable("name") String name){
        try {
            PatternResponse response = esperService.deployPattern(name);

            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);

            return new ResponseEntity<>("This pattern hasn't been compiled yet", HttpStatus.NOT_FOUND);

        } catch (EPDeployException e) {
            return new ResponseEntity<>("Failed during deploying, this pattern has been deployed previously or it use an undefined schema" , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/undeploy_pattern/{id}")
    public ResponseEntity<?> undeployPattern(@PathVariable("id") String id){
        String response = esperService.undeploy(id,"Pattern");

        if (response != null)
            return new ResponseEntity<>("The pattern "+ response + " has been removed successfully", HttpStatus.OK);

        return new ResponseEntity<>("There is any pattern deployed with the given id", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pattern_is_deployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        return esperService.isDeployed(id);
    }

    @GetMapping("/deployed_patterns")
    public ResponseEntity<?> getDeployedEventTypes(){
        List<String> response = esperService.getDeployed("Pattern");
        return !response.isEmpty() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>("There is no event type deployed", HttpStatus.NOT_FOUND);
    }

}
