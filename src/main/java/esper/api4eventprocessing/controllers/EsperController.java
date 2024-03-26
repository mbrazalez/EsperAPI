package esper.api4eventprocessing.controllers;

import esper.api4eventprocessing.petitions.EventPetition;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.service.EsperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EsperController {

    @Autowired
    private EsperService esperService;

    @PostMapping("/new_schema")
    public String newEventTypeJson(@RequestBody EventTypePetition newEventType){
       return esperService.newEventTypeJson(newEventType);
    }

    @PutMapping("/deploy_schema/{name}")
    public String deploy(@PathVariable("name") String name){
        return esperService.deployEventType(name);
    }

    @GetMapping("/deployed_eventtypes")
    public String[] getDeployedEventTypes(){
        return esperService.getDeployedEventTypes();
    }

    @GetMapping("/isdeployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        System.out.printf(id);
        return esperService.isDeployed(id);
    }

    @DeleteMapping("/undeploy/{id}")
    public String undeployEventType(@PathVariable("id")String id){
        return esperService.undeployEventType(id);
    }

    @PostMapping("/send_event_json")
    public String sendEventJson(@RequestBody EventPetition eventPetition){
        return esperService.sendEventJson(eventPetition.eventTypeName, eventPetition.content);
    }

}

