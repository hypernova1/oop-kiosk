package kr.co._29cm.homework.common.repository;

public class PrimaryKeyNotFoundException extends RuntimeException {
    public PrimaryKeyNotFoundException() {
        super("고유키가 존재하지 않습니다.");
    }
}
