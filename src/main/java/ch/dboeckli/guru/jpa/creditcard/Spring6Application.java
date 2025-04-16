package ch.dboeckli.guru.jpa.creditcard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Spring6Application {

    public static void main(String[] args) {
        log.info("Starting Spring 6 Application...");
        SpringApplication.run(Spring6Application.class, args);
    }

}
