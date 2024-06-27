package kr.co._29cm.homework.common.lock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class MemoryLockManagerTest {

    private static final String LOCK_KEY = "melchor";

    private final LockManager lockManager = new MemoryLockManager();

    void release() {
        if (lockManager.exists(LOCK_KEY)) {
            lockManager.release(LOCK_KEY);
        }
    }

    @Test
    void test_denied_duplication_key() {
        lockManager.set(LOCK_KEY);
        assertThatExceptionOfType(AlreadyLockException.class)
                .isThrownBy(() -> lockManager.set(LOCK_KEY));
    }

    @Test
    void test_release() {
        lockManager.set(LOCK_KEY);
        lockManager.release(LOCK_KEY);

        assertDoesNotThrow(() -> lockManager.set(LOCK_KEY));
    }

}