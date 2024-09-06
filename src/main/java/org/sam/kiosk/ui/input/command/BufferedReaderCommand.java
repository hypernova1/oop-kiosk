package org.sam.kiosk.ui.input.command;

import org.sam.kiosk.common.exception.NotNumberException;
import org.sam.kiosk.util.NumberUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BufferedReaderCommand implements Command {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    private final String value;

    private BufferedReaderCommand(String value) {
        if (value == null) {
            throw new CommandNotFoundException();
        }
        this.value = value;
    }

    /**
     * 명령을 호출한다.
     * */
    public static BufferedReaderCommand call() {
        try {
            return new BufferedReaderCommand(READER.readLine());
        } catch (IOException e) {
            throw new BadCommandException();
        }
    }

    /**
     * 주문을 완료할 것인지 여부를 확인한다.
     * */
    public boolean isCompleteOrder() {
        return InputCommandType.COMPLETE_ORDER.equals(this);
    }

    /**
     * 프로그램을 종료할 것인지 여부를 확인한다.
     * */
    public boolean isProgramTerminated() {
        if (InputCommandType.TERMINATED_PROGRAM.equals(this)) {
            return true;
        } else if (InputCommandType.CONTINUE_ORDER.equals(this)) {
            return false;
        }
        throw new BadCommandException();
    }

    /**
     * 받은 값을 int로 변환한다.
     * */
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
