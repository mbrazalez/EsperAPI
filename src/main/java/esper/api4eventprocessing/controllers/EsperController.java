package esper.api4eventprocessing.controllers;

import esper.api4eventprocessing.petitions.EventPetition;
import esper.api4eventprocessing.petitions.EventTypePetition;
import esper.api4eventprocessing.petitions.PatternPetition;
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

    @PutMapping("/deploy_eventtype/{name}")
    public String deployEventType(@PathVariable("name") String name){
        return esperService.deployEventType(name);
    }

    @GetMapping("/deployed_eventtypes")
    public String[] getDeployedEventTypes(){
        return esperService.getDeployedEventTypes();
    }

    @GetMapping("/isdeployed/{id}")
    public boolean isDeployed(@PathVariable("id") String id){
        return esperService.isDeployed(id);
    }

    @DeleteMapping("/undeploy_eventtype/{id}")
    public String undeployEventType(@PathVariable("id")String id){
        return esperService.undeploy(id);
    }

    @PostMapping("/send_event_json")
    public String sendEventJson(@RequestBody EventPetition eventPetition){
        return esperService.sendEventJson(eventPetition);
    }

    @PostMapping("/add_pattern")
    public String addNewPattern(@RequestBody PatternPetition patternPetition){
        return esperService.addNewPattern(patternPetition);
    }

    @PutMapping("/deploy_pattern/{name}")
    public String deployPattern(@PathVariable("name") String name){
        return esperService.deployPattern(name);
    }

    @DeleteMapping("/undeploy_pattern/{id}")
    public String undeployPattern(@PathVariable("id")String id){
        return esperService.undeploy(id);
    }

}

