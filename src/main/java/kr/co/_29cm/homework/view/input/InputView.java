package kr.co._29cm.homework.view.input;

public class InputView {

    private InputView() {}

    /**
     * 상품 번호를 입력받는다.
     *
     * @return 상품 번호
     * */
    public static Command inputProductNoOrCompleteOrder() {
        System.out.print("상품번호 : ");
        return Command.call();
    }

    /**
     * 구매할 상품 수량을 입력받는다.
     *
     * @return 구매할 상품 수량
     * */
    public static Command inputQuantity() {
        System.out.print("수량 : ");
        return Command.call();
    }

    /**
     * 프로그램을 종료할지 여부를 입력받는다.
     * */
    public static Command inputTerminatedOrOrderContinue() {
        System.out.println("계속 주문 하시겠습니까? o[order]: 주문, q[quit]: 종료");
        return Command.call();
    }

}
