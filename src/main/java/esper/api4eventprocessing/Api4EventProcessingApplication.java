package esper.api4eventprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Api4EventProcessingApplication {
    private static final Logger log = LoggerFactory.getLogger(Api4EventProcessingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Api4EventProcessingApplication.class, args);
    }
}