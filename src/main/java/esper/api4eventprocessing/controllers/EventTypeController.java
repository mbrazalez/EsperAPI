package esper.api4eventprocessing.controllers;

import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.responses.EventTypeResponse;
import esper.api4eventprocessing.services.EsperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventTypeController {

    @Autowired
    private EsperService esperService;

    @PostMapping("/new_eventtype_json")
    public ResponseEntity<EventTypeResponse> newEventTypeJson(@RequestBody EventTypePetition newEventType){
        try {
            EventTypeResponse response = esperService.newEventTypeJson(newEventType);
            return ResponseEntity.ok(response);
        } catch (EPCompileException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deploy_eventtype/{name}")
    public ResponseEntity<EventTypeResponse> deployEventType(@PathVariable("name") String name){
        try {

            return esperService.deployEventType(name);
        } catch (EPDeployException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/deployed_eventtypes")
    public String[] getDeployedEventTypes(){
        return esperService.getDeployedEventTypes();
    }

    @GetMapping("/eventtype_deployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        return esperService.isDeployed(id);
    }

    @DeleteMapping("/undeploy_eventtype/{id}")
    public String undeployEventType(@PathVariable("id")String id){
        return esperService.undeploy(id);
    }

}

