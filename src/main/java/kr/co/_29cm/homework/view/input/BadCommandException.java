package kr.co._29cm.homework.view.input;

public class BadCommandException extends RuntimeException {
    public BadCommandException() {
        super("잘못된 입력 값입니다.");
    }

    public BadCommandException(String message) {
        super(message);
    }

}
