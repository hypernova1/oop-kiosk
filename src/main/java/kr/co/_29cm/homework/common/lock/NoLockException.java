package kr.co._29cm.homework.common.lock;

public class NoLockException extends RuntimeException {
    public NoLockException() {
        super("락이 존재하지 않습니다.");
    }
}
