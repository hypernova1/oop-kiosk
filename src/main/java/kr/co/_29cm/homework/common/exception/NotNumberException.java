package kr.co._29cm.homework.common.exception;

import kr.co._29cm.homework.view.input.BadCommandException;

public class NotNumberException extends BadCommandException {
    public NotNumberException() {
        super("숫자가 아닙니다.");
    }
}
