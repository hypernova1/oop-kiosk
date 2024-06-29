package kr.co._29cm.homework.common.lock;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

@Component
public class MemoryLockManager implements LockManager {

    private static final long DEFAULT_ADDITIONAL_MILLISECONDS = 1000 * 60L;
    private final Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public void acquire(String id) {
        long startTime = System.currentTimeMillis();
        long timeout = 1000 * 30;
        long elapsedTime = 0;

        synchronized (locks) {
            while (exists(id)) {
                try {
                    long waitTime = timeout - elapsedTime;
                    if (waitTime <= 0) {
                        throw new TimeoutException("락 획득 실패. id: " + id);
                    }
                    locks.wait(waitTime);
                    elapsedTime = System.currentTimeMillis() - startTime;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
            set(id);
        }

    }

    @Override
    public void acquireList(List<String> ids) {
        ids.forEach(this::acquire);
    }

    @Override
    public void set(String id) {
        this.set(id, DEFAULT_ADDITIONAL_MILLISECONDS);
    }

    public synchronized void set(String id, long additionalMilliSeconds) {
        if (exists(id)) {
            throw new AlreadyLockException();
        }
        Lock lock = new Lock(id, additionalMilliSeconds);
        locks.put(id, lock);
    }

    @Override
    public void release(String id) {
        synchronized (locks) {
            locks.remove(id);
            locks.notifyAll();
        }
    }

    @Override
    public void releaseList(List<String> ids) {
        for (String id : ids) {
            this.release(id);
        }
    }

    @Override
    public synchronized boolean exists(String id) {
        Optional<Lock> lock = this.findLock(id);
        if (lock.isEmpty()) {
            return false;
        }
        if (lock.get().isExpired()) {
            release(id);
            return false;
        }
        return true;
    }

    public Lock get(String id) {
        return this.findLock(id).orElseThrow(NoLockException::new);
    }

    private Optional<Lock> findLock(String id) {
        Lock value = this.locks.get(id);
        return Optional.ofNullable(value);
    }

}
