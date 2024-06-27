package kr.co._29cm.homework.common.exception;

public class NotNumberException extends RuntimeException {
    public NotNumberException() {
        super("숫자가 아닙니다.");
    }
}
