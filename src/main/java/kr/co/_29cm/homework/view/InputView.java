package kr.co._29cm.homework.view;

import kr.co._29cm.homework.common.exception.NotNumberException;
import kr.co._29cm.homework.util.NumberUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InputView {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    private InputView() {}

    public static String inputProductNo() {
        System.out.print("상품번호 : ");
        String input = readLine();
        if (InputCommand.EXIT.equals(input)) {
            return "";
        }
        return input.trim();
    }

    public static int inputQuantity() {
        System.out.print("수량 : ");
        String input = readLine();
        if (InputCommand.EXIT.equals(input)) {
            return 0;
        }

        if (!NumberUtil.isInteger(input)) {
            throw new NotNumberException();
        }

        return Integer.parseInt(input);
    }

    public static boolean isContinue() {
        String input = readLine().trim();
        if (InputCommand.CONTINUE_ORDER.equals(input)) {
            return true;
        } else if (InputCommand.QUIT.equals(input)) {
            return false;
        }

        throw new BadCommandException();
    }

    public static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
