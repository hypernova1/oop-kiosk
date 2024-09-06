package org.sam.kiosk.common.lock;

import java.util.List;

public interface LockManager {
    void set(String key);
    void acquire(String key);
    void acquireList(List<String> keys);
    void release(String id);
    void releaseList(List<String> keys);
    boolean exists(String key);
}
