package kr.co._29cm.homework.common.lock;

public class AlreadyLockException extends RuntimeException {
    public AlreadyLockException() {
        super("이미 락이 존재합니다.");
    }
}
