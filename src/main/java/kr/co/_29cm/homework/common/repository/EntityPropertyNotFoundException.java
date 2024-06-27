package kr.co._29cm.homework.common.repository;

public class EntityPropertyNotFoundException extends RuntimeException {
    public EntityPropertyNotFoundException() {
        super("프로퍼티가 존재하지 않습니다.");
    }
}
