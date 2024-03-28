package esper.api4eventprocessing.controllers;

import esper.api4eventprocessing.events.PM10Event;
import esper.api4eventprocessing.petitions.EventJsonPetition;
import esper.api4eventprocessing.services.EsperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private EsperService esperService;

    @PostMapping("/send_event_json")
    public String sendEventJson(@RequestBody EventJsonPetition eventJsonPetition){
        return esperService.sendEventJson(eventJsonPetition);
    }

    @PostMapping("/send_pm10event")
    public String pm10Event(@RequestBody PM10Event pm10Event){
        return "PM10Event received from MQTT Broker";
    }


}
