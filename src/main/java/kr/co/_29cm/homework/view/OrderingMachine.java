package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.CartEmptyException;
import kr.co._29cm.homework.order.domain.NoOrderItemException;
import kr.co._29cm.homework.order.payload.OrderResponse;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductDto;
import kr.co._29cm.homework.view.input.*;
import kr.co._29cm.homework.view.input.command.BadCommandException;
import kr.co._29cm.homework.view.input.command.Command;
import kr.co._29cm.homework.view.input.command.CommandNotFoundException;
import kr.co._29cm.homework.view.output.Output;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingMachine {

    private final OrderProcessHandler orderProcessHandler;
    private final Input input;
    private final Output output;


    /**
     * 주문 프로세스를 시작한다.
     */
    public void process(String userId) {
        printProducts();
        OrderProcess orderProcess = OrderProcess.start();
        boolean isRunning = true;
        while (isRunning) {
            try {
                switch (orderProcess) {
                    case ADD_PRODUCT -> orderProcess = addProduct(userId);
                    case COMPLETE_ORDER -> orderProcess = completeOrder(userId);
                    case CONTINUE_OR_QUIT -> orderProcess = continueOrderOrQuit();
                    case QUIT -> isRunning = false;
                }
            } catch (ProductNotFoundException | CartEmptyException | NoOrderItemException e) {
                orderProcessHandler.clearCart(userId);
                orderProcess = OrderProcess.ADD_PRODUCT;
            } catch (SoldOutException e) {
                output.printException(e);
                orderProcessHandler.clearCart(userId);
                orderProcess = OrderProcess.CONTINUE_OR_QUIT;
            } catch (BadCommandException | CommandNotFoundException e) {
                output.printException(e);
            }
        }
        output.printQuitMessage();
    }

    /**
     * 상품 목록을 출력한다.
     * */
    private void printProducts() {
        List<ProductDto> products = orderProcessHandler.getAllProducts();
        output.printProducts(products);
    }

    /**
     * 장바구니에 상품을 추가한다.
     *   " "를 입력받으면 해당 주문을 완료한다.
     * */
    protected OrderProcess addProduct(String userId) {
        Command command = input.inputProductNoOrIsCompleteOrder();
        if (command.isCompleteOrder()) {
            if (this.orderProcessHandler.existCartItems(userId)) {
                return OrderProcess.COMPLETE_ORDER;
            }
            return OrderProcess.CONTINUE_OR_QUIT;
        }

        String productNo = command.toString();
        command = input.inputQuantity();
        int quantity = command.toInt();
        orderProcessHandler.addProductToCart(userId, productNo, quantity);
        return OrderProcess.ADD_PRODUCT;
    }

    /**
     * 주문을 완료한다.
     * */
    protected OrderProcess completeOrder(String userId) {
        OrderResponse orderResponse = orderProcessHandler.createOrder(userId);
        output.printOrderDetail(orderResponse);
        return OrderProcess.CONTINUE_OR_QUIT;
    }

    /**
     * 계속할지 종료할지를 선택한다.
     * */
    protected OrderProcess continueOrderOrQuit() {
        boolean isProgramTerminated = input.inputProgramTerminatedOrOrderContinue().isProgramTerminated();
        return isProgramTerminated ? OrderProcess.QUIT : OrderProcess.ADD_PRODUCT;
    }

}
