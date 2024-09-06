package org.sam.kiosk.ui;

import org.sam.kiosk.ui.view.Kiosk;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Profile("!test")
@Component
public class KioskRunner implements CommandLineRunner {

    private final Kiosk kiosk;

    @Override
    public void run(String... args) throws Exception {
        String userId = UUID.randomUUID().toString();
        kiosk.process(userId);
    }
}
