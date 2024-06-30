package kr.co._29cm.homework.ui.input;

import kr.co._29cm.homework.ui.input.command.BufferedReaderCommand;
import kr.co._29cm.homework.ui.input.command.Command;
import org.springframework.stereotype.Component;

@Component
public class InputView implements Input {

    /**
     * 상품 번호를 입력받는다.
     *
     * @return 상품 번호
     * */
    public Command inputProductNoOrIsCompleteOrder() {
        System.out.print("상품번호 : ");
        return BufferedReaderCommand.call();
    }

    /**
     * 구매할 상품 수량을 입력받는다.
     *
     * @return 구매할 상품 수량
     * */
    public Command inputQuantity() {
        System.out.print("수량 : ");
        return BufferedReaderCommand.call();
    }

    /**
     * 프로그램을 종료할지 여부를 입력받는다.
     * */
    public Command inputProgramTerminatedOrOrderContinue() {
        System.out.println("계속 주문 하시겠습니까? o[order]: 주문, q[quit]: 종료");
        return BufferedReaderCommand.call();
    }

}
