package kr.co._29cm.homework.common.lock;

import java.util.List;

public interface LockManager {
    void set(String id);
    void acquire(String id);
    void acquireList(List<String> ids);
    void release(String id);
    void releaseList(List<String> ids);
    boolean exists(String id);
}
