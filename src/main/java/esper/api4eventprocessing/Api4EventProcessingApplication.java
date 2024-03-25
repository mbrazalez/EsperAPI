package esper.api4eventprocessing;

import esper.api4eventprocessing.events.PM10Event;
import esper.api4eventprocessing.repository.EsperRepository;
import esper.api4eventprocessing.service.EsperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Api4EventProcessingApplication {
    private static final Logger log = LoggerFactory.getLogger(Api4EventProcessingApplication.class);

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Api4EventProcessingApplication.class, args);


    }

}
