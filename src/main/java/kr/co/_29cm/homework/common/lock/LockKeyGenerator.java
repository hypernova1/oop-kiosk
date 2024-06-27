package kr.co._29cm.homework.common.lock;

import java.util.List;
import java.util.stream.Collectors;

public class LockKeyGenerator {
    public static String generateLockKey(LockType lockType, String key) {
        return lockType.getName() + "_" + key;
    }

    public static List<String> generateLockKeyList(LockType lockType, List<String> keys) {
        return keys.stream().map((key) -> generateLockKey(lockType, key)).collect(Collectors.toList());
    }
}
