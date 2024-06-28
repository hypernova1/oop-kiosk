package kr.co._29cm.homework.view.input;

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
    public static Command inputProductNoOrCompleteOrder() {
        System.out.print("상품번호 : ");
        return new Command(readLine());
    }

    /**
     * 구매할 상품 수량을 입력받는다.
     *
     * @return 구매할 상품 수량
     * */
    public static Command inputQuantity() {
        System.out.print("수량 : ");
        return new Command(readLine());
    }

    /**
     * 주문을 계속할지 여부를 입력받는다.
     * */
    public static Command isOrderContinue() {
        System.out.println("계속 주문 하시겠습니까? o[order]: 주문, q[quit]: 종료");
        return new Command(readLine().trim());
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
