package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.payment.payload.PaymentDto;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public class OrderingMachine {

    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderingMachine(ProductService productService, OrderService orderService, PaymentService paymentService) {
        this.productService = productService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    /**
     * 주문 프로세스를 시작한다.
     */
    public void process() {
        Cart cart = new Cart();

        List<ProductDto> products = productService.getAll();
        OutputView.printProducts(products);
        boolean process = true;
        while (process) {
            try {
                String productNo = InputView.inputProductNoOrCompleteOrder();
                if (InputCommand.COMPLETE_ORDER.equals(productNo)) {
                    createOrder(cart);
                    process = InputView.isOrderContinue();
                } else {
                    int quantity = InputView.inputQuantity();
                    addCartProduct(productNo, quantity, cart);
                }
            } catch (BadCommandException e) {
                OutputView.printBadInput();
            } catch (ProductNotFoundException | SoldOutException e) {
                OutputView.printException(e);
            }
        }

        OutputView.thanksToCustomer();
    }

    /**
     * 장바구니에 상품을 추가한다.
     * */
    private void addCartProduct(String productNo, int quantity, Cart cart) {
        validateOrderData(productNo, quantity);
        ProductDto product = productService.findOne(productNo);
        cart.addProduct(product, quantity);
    }

    /**
     * 주문을 생성한다.
     *
     * @param cart 장바구니
     * */
    private void createOrder(Cart cart) {
        OutputView.printCartProducts(cart.getProducts());
        String orderNo = orderService.create(cart);
        cart.clear();

        PaymentDto payment = this.paymentService.findOne(orderNo);
        OutputView.printPayment(payment);
    }

    /**
     * 주문 데이터를 검증한다.
     *
     * @param productNo 상품 번호
     * @param quantity  주문 수량
     */
    public void validateOrderData(String productNo, int quantity) {
        boolean isValid = (productNo.isEmpty() && quantity == -1) || (!productNo.isEmpty() && quantity > 0);
        if (!isValid) {
            throw new BadCommandException();
        }
    }
}
