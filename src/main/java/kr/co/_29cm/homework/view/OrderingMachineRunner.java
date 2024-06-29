package kr.co._29cm.homework.view;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class OrderingMachineRunner implements CommandLineRunner {

    private final OrderingMachine orderingMachine;

    @Override
    public void run(String... args) throws Exception {
        String userId = UUID.randomUUID().toString();
        orderingMachine.process(userId);
    }
}
