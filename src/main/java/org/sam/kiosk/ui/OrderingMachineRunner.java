package org.sam.kiosk.ui;

import org.sam.kiosk.ui.view.OrderingMachine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Profile("!test")
@Component
public class OrderingMachineRunner implements CommandLineRunner {

    private final OrderingMachine orderingMachine;

    @Override
    public void run(String... args) throws Exception {
        String userId = UUID.randomUUID().toString();
        orderingMachine.process(userId);
    }
}
