package org.sam.kiosk.common.exception;

import org.sam.kiosk.ui.input.command.BadCommandException;

public class NotNumberException extends BadCommandException {
    public NotNumberException() {
        super("숫자가 아닙니다.");
    }
}
