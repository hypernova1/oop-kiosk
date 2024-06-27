package kr.co._29cm.homework.view;

import kr.co._29cm.homework.common.exception.NotNumberException;
import kr.co._29cm.homework.util.NumberUtil;

import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputView() {}

    public static String inputProductNo() {
        System.out.print("상품번호 : ");
        String input = SCANNER.nextLine();
        if (InputCommand.EXIT.equals(input)) {
            return "";
        }
        return input.trim();
    }

    public static int inputQuantity() {
        System.out.print("수량 : ");
        String input = SCANNER.nextLine();
        if (InputCommand.EXIT.equals(input)) {
            return 0;
        }

        if (!NumberUtil.isInteger(input)) {
            throw new NotNumberException();
        }

        return Integer.parseInt(input);
    }

    public static boolean isContinue() {
        String input = SCANNER.nextLine().trim();
        if (InputCommand.CONTINUE_ORDER.equals(input)) {
            return true;
        } else if (InputCommand.QUIT.equals(input)) {
            return false;
        }

        throw new BadCommandException();
    }



}
