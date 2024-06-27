package kr.co._29cm.homework.view;

public class BadCommandException extends RuntimeException {
    public BadCommandException() {
        super("잘못된 입력 값입니다.");
    }
}
