package esper.api4eventprocessing.controllers;

import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.runtime.client.EPDeployException;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.responses.EventTypeResponse;
import esper.api4eventprocessing.services.EsperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventTypeController {

    private final EsperService esperService;

    public EventTypeController(EsperService esperService) {
        this.esperService = esperService;
    }

    @PostMapping("/new_event-type_json")
    public ResponseEntity<?> newEventTypeJson(@RequestBody EventTypePetition newEventType){
        String name = newEventType.name;
        String schema = newEventType.schema;

        if ( name == null ||  schema == null)
            return new ResponseEntity<>("Parameters missing in the request body",HttpStatus.BAD_REQUEST);

        try {
            EventTypeResponse response = esperService.newEventTypeJson(name, schema);

            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            response = new EventTypeResponse(name, schema, "This event type has been already compiled");
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);

        } catch (EPCompileException e) {
            return new ResponseEntity<>("Failed to compile event type due to syntax or other esper compilation errors." , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deploy_event-type/{name}")
    public ResponseEntity<?> deployEventType(@PathVariable("name") String eventTypeName){
        try {
            EventTypeResponse response = esperService.deployEventType(eventTypeName);

            if (response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);

            return new ResponseEntity<>("This event type hasn't been compiled yet", HttpStatus.NOT_FOUND);

        } catch (EPDeployException e) {
           return new ResponseEntity<>("Failed during deploying, this event type has been deployed previously" , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deployed_event-types")
    public ResponseEntity<?> getDeployedEventTypes(){
        List<String> response = esperService.getDeployed("EventType");
        return !response.isEmpty() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>("There is no event type deployed", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/event-type_is_deployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        return esperService.isDeployed(id);
    }

    @DeleteMapping("/undeploy_event-type/{id}")
    public ResponseEntity<?> undeployEventType(@PathVariable("id") String id){
        String response =  esperService.undeploy(id,"EventType");

        if (response != null)
            return new ResponseEntity<>("The event type " + response + " has been removed successfully", HttpStatus.OK);

        return new ResponseEntity<>("There is any event type deployed with the given id or there is a pattern using this event type", HttpStatus.NOT_FOUND);
    }

}

