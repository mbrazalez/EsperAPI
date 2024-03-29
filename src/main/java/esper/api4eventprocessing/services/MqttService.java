package esper.api4eventprocessing.services;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private final IMqttClient mqttClient;

    @Autowired
    public MqttService(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
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
        System.out.printf(payload);
        System.out.printf("LLega chicha!!!");
        // Example of dispatching:
        switch(topic) {
            case "pm10topic":
                // Deserialize payload to PM10Event
                // Call pm10Event endpoint logic or service directly
                break;
            case "pm25topic":
                // Deserialize payload to PM25Event
                // Call pm25Event endpoint logic or service directly
                break;
            // Handle other cases...
        }
    }
}