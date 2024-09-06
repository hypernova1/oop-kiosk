package org.sam.kiosk.ui.view;

import org.sam.kiosk.cart.domain.CartEmptyException;
import org.sam.kiosk.order.domain.NoOrderItemException;
import org.sam.kiosk.order.payload.OrderResponse;
import org.sam.kiosk.product.domain.ProductNotFoundException;
import org.sam.kiosk.product.domain.SoldOutException;
import org.sam.kiosk.product.payload.ProductDto;
import kr.co._29cm.homework.ui.input.*;
import org.sam.kiosk.ui.input.Input;
import org.sam.kiosk.ui.input.command.BadCommandException;
import org.sam.kiosk.ui.input.command.Command;
import org.sam.kiosk.ui.input.command.CommandNotFoundException;
import org.sam.kiosk.ui.output.Output;
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
                output.printException(e);
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
     */
    private void printProducts() {
        List<ProductDto> products = orderProcessHandler.getAllProducts();
        output.printProducts(products);
    }

    /**
     * 장바구니에 상품을 추가한다.
     * " "를 입력받으면 해당 주문을 완료한다.
     */
    protected OrderProcess addProduct(String userId) {
        Command command = input.inputProductNoOrIsCompleteOrder();
        if (!command.isCompleteOrder()) {
            String productNo = command.toString();
            command = input.inputQuantity();
            int quantity = command.toInt();
            orderProcessHandler.addProductToCart(userId, productNo, quantity);
            return OrderProcess.ADD_PRODUCT;
        }

        if (this.orderProcessHandler.existCartItems(userId)) {
            return OrderProcess.COMPLETE_ORDER;
        }
        return OrderProcess.CONTINUE_OR_QUIT;
    }

    /**
     * 주문을 완료한다.
     */
    protected OrderProcess completeOrder(String userId) {
        OrderResponse orderResponse = orderProcessHandler.createOrder(userId);
        output.printOrderDetail(orderResponse);
        return OrderProcess.CONTINUE_OR_QUIT;
    }

    /**
     * 계속할지 종료할지를 선택한다.
     */
    protected OrderProcess continueOrderOrQuit() {
        boolean isProgramTerminated = input.inputProgramTerminatedOrOrderContinue().isProgramTerminated();
        return isProgramTerminated ? OrderProcess.QUIT : OrderProcess.ADD_PRODUCT;
    }

}
