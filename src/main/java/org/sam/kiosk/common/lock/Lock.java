package org.sam.kiosk.common.lock;

import java.time.LocalDateTime;

public class Lock {
    private final String id;
    private LocalDateTime expiredTime;

    public Lock(String id, long additionalMilliSeconds) {
        this.id = id;
        this.expiredTime = LocalDateTime.now().plusNanos(additionalMilliSeconds * 1000);
    }

    public String getId() {
        return this.id;
    }

    public void extendExpiredTime(long additionalNanoTime) {
        this.expiredTime = this.expiredTime.plusNanos(additionalNanoTime);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredTime);
    }

    public LocalDateTime getExpiredTime() {
        return this.expiredTime;
    }
}
