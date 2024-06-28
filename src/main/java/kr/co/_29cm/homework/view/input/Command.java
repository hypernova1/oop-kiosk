package kr.co._29cm.homework.view.input;

import kr.co._29cm.homework.common.exception.NotNumberException;
import kr.co._29cm.homework.util.NumberUtil;

public class Command {
    private final String value;

    public Command(String value) {
        if (value == null) {
            throw new CommandNotFoundException();
        }
        this.value = value;
    }

    public boolean isCompleteOrder() {
        return InputCommandType.COMPLETE_ORDER.equals(this);
    }

    public boolean isOrderContinue() {
        if (InputCommandType.CONTINUE_ORDER.equals(this)) {
            return true;
        } else if (InputCommandType.QUIT.equals(this)) {
            return false;
        }
        throw new BadCommandException();
    }

    public int toInt() {
        if (!NumberUtil.isInteger(this.value)) {
            throw new NotNumberException();
        }

        return Integer.parseInt(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
