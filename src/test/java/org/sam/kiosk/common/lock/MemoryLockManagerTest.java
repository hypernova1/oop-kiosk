package org.sam.kiosk.common.lock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class MemoryLockManagerTest {

    private static final String LOCK_KEY = "melchor";

    private final LockManager lockManager = new MemoryLockManager();

    @BeforeEach
    void release() {
        if (lockManager.exists(LOCK_KEY)) {
            lockManager.release(LOCK_KEY);
        }
    }

    @Test
    void test_denied_duplication_key() {
        //given
        lockManager.set(LOCK_KEY);

        //when, then
        assertThatExceptionOfType(AlreadyLockException.class)
                .isThrownBy(() -> lockManager.set(LOCK_KEY));
    }

    @Test
    void test_release() {
        //given
        lockManager.set(LOCK_KEY);
        lockManager.release(LOCK_KEY);

        //when, then
        assertDoesNotThrow(() -> lockManager.set(LOCK_KEY));
    }

}