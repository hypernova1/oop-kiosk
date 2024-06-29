package kr.co._29cm.homework;


import kr.co._29cm.homework.view.OrderingMachine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OrderingMachine orderingMachine) {
        String userId = UUID.randomUUID().toString();
        return args -> orderingMachine.process(userId);
    }
}