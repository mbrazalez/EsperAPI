package esper.api4eventprocessing.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import esper.api4eventprocessing.events.HumidityEvent;
import esper.api4eventprocessing.events.PM10Event;
import esper.api4eventprocessing.events.PM25Event;
import esper.api4eventprocessing.events.WindSpeedEvent;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private final IMqttClient mqttClient;
    private final EsperService esperService;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    public MqttService(IMqttClient mqttClient, EsperService esperService) {
        this.mqttClient = mqttClient;
        this.esperService = esperService;
    }

    public void subscribeToTopics() throws Exception {
        if (!mqttClient.isConnected()) {
            throw new RuntimeException("MQTT client not connected");
        }
        mqttClient.subscribe("pm10topic", this::handleMessage);
        mqttClient.subscribe("pm25topic", this::handleMessage);
        mqttClient.subscribe("humiditytopic", this::handleMessage);
        mqttClient.subscribe("windtopic", this::handleMessage);
    }

    private void handleMessage(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        System.out.printf(payload+"\n");

        switch(topic) {
            case "pm10topic":
                try{
                    PM10Event pm10Event =  this.objectMapper.readValue(payload, PM10Event.class);
                    esperService.sendEvent(pm10Event,"PM10Event");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "pm25topic":
                PM25Event pm25Event = this.objectMapper.readValue(payload, PM25Event.class);
                esperService.sendEvent(pm25Event,"PM25Event");
                break;
            case "humiditytopic":
                HumidityEvent humidityEvent = this.objectMapper.readValue(payload, HumidityEvent.class);
                esperService.sendEvent(humidityEvent,"HumidityEvent");
                break;
            case "windtopic":
                WindSpeedEvent windSpeedEvent = this.objectMapper.readValue(payload, WindSpeedEvent.class);
                esperService.sendEvent(windSpeedEvent,"WindSpeedEvent");
                break;
        }
    }
}