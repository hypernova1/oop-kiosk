package kr.co._29cm.homework.view.input.command;

public class BadCommandException extends RuntimeException {
    public BadCommandException(String message) {
        super(message);
    }
    public BadCommandException() {
        this("잘못된 입력 값입니다.");
    }
}
