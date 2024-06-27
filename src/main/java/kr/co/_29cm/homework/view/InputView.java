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

    /**
     * 상품 번호를 입력받는다.
     *
     * @return 상품 번호
     * */
    public static String inputProductNo() {
        System.out.print("상품번호 : ");
        String input = readLine();
        return input.trim();
    }

    /**
     * 구매할 상품 수량을 입력받는다.
     *
     * @return 구매할 상품 수량
     * */
    public static int inputQuantity() {
        System.out.print("수량 : ");
        String input = readLine();
        if (InputCommand.COMPLETE_ORDER.equals(input)) {
            return 0;
        }

        if (!NumberUtil.isInteger(input)) {
            throw new NotNumberException();
        }

        int quantity = Integer.parseInt(input);
        if (quantity == 0) {
            //TODO: 올바른 익셉션으로 변경해야 함
            throw new NotNumberException();
        }
        return quantity;
    }

    /**
     * 주문을 계속할지 여부를 입력받는다.
     * */
    public static boolean isOrderContinue() {
        String input = readLine().trim();
        if (InputCommand.CONTINUE_ORDER.equals(input)) {
            return true;
        } else if (InputCommand.QUIT.equals(input)) {
            return false;
        }

        throw new BadCommandException();
    }

    /**
     * 문자열을 입력받는다.
     *
     * @return 입력받은 문자열
     * */
    public static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
