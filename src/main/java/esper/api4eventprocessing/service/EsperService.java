package esper.api4eventprocessing.service;

import esper.api4eventprocessing.repository.EsperRepository;
import org.springframework.stereotype.Service;

@Service
public class EsperService {
    private EsperRepository esperRepository;

    public EsperService() {
        this.esperRepository = EsperRepository.getInstance();
    }

    public String addEventType(String eventName, String eventType) {
        return esperRepository.addEventType(eventName, eventType);
    }

    public String deployPattern(String epl) {
        return esperRepository.deployPattern(epl);
    }

    public void sendEvent(Object event) {
        esperRepository.getRuntime().getEventService().sendEventBean(event, event.getClass().getSimpleName());
    }

    public void getPattern(String deploymentId) {
        System.out.println("Functionality to get a single pattern by ID is not directly supported by Esper.");
    }

    public void getPatterns() {
        System.out.println("Functionality to list all deployed patterns is not directly supported by Esper.");
    }

    public String deletePattern(String deploymentId) {
        try {
            esperRepository.getRuntime().getDeploymentService().undeploy(deploymentId);
            return "Pattern undeployed successfully.";
        } catch (Exception e) {
            return "Error undeploying pattern: " + e.getMessage();
        }
    }

}
