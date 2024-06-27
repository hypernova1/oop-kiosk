package kr.co._29cm.homework.common.repository;

public class PrimaryKeyTypeNotMatchException extends RuntimeException {
    public PrimaryKeyTypeNotMatchException() {
        super("고유키의 타입이 일치하지 않습니다.");
    }
}
