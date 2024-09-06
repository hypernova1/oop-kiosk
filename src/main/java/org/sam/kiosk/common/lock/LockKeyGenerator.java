package org.sam.kiosk.common.lock;

import java.util.List;

public class LockKeyGenerator {
    public static String generateLockKey(LockType lockType, String key) {
        return lockType.getName() + "_" + key;
    }

    public static List<String> generateLockKeyList(LockType lockType, List<String> keys) {
        return keys.stream().map((key) -> generateLockKey(lockType, key)).toList();
    }
}
