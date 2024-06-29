package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.CartEmptyException;
import kr.co._29cm.homework.order.domain.NoOrderItemException;
import kr.co._29cm.homework.order.payload.OrderResponse;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductDto;
import kr.co._29cm.homework.view.input.BadCommandException;
import kr.co._29cm.homework.view.input.Command;
import kr.co._29cm.homework.view.input.InputView;
import kr.co._29cm.homework.view.output.OutputView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingMachine {

    private final OrderProcessHandler orderProcessHandler;


    /**
     * 주문 프로세스를 시작한다.
     */
    public void process(String userId) {
        orderProcessHandler.loadCart(userId);

        List<ProductDto> products = orderProcessHandler.getAllProducts();
        OutputView.printProducts(products);

        while (true) {
            try {
                Command productNoOrIsCompleteOrderInput = InputView.inputProductNoOrIsCompleteOrder();
                if (!productNoOrIsCompleteOrderInput.isCompleteOrder()) {
                    Command quantityInput = InputView.inputQuantity();
                    orderProcessHandler.addProductToCart(userId, productNoOrIsCompleteOrderInput.toString(), quantityInput.toInt());
                    continue;
                }

                OrderResponse orderResponse = orderProcessHandler.createOrder(userId);
                OutputView.printOrderDetail(orderResponse);

                boolean isProgramTerminated = InputView.inputPogramTerminatedOrOrderContinue().isProgramTerminated();
                if (isProgramTerminated) {
                    break;
                }
            } catch (BadCommandException | ProductNotFoundException | CartEmptyException | NoOrderItemException e) {
                OutputView.printException(e);
            } catch (SoldOutException e) {
                OutputView.printException(e);
                orderProcessHandler.clearCart(userId);
            }
        }

        OutputView.printThanksToCustomer();
    }

}
