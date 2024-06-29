package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.order.payload.OrderRequest;
import kr.co._29cm.homework.order.payload.OrderResponse;
import kr.co._29cm.homework.order.payload.OrderRequestItem;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.payment.payload.PaymentResponse;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductDto;
import kr.co._29cm.homework.view.input.BadCommandException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 주문 프로세스를 담당하는 핸들러
 *
 * @see kr.co._29cm.homework.view.OrderingMachine
 * */
@Service
public class OrderProcessHandler {

    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final Cart cart;

    public OrderProcessHandler(ProductService productService, OrderService orderService, PaymentService paymentService) {
        this.productService = productService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.cart = new Cart();
    }

    /**
     * 장바구니에 상품을 추가한다.
     *
     * @param productNo 상품
     * @param quantity 구매할 수량
     * @param cart 장바구니
     */
    public void addProductToCart(String productNo, int quantity) {
        validateOrderData(productNo, quantity);
        ProductDto product = productService.findOne(productNo);
        cart.addProduct(product, quantity);
    }

    /**
     * 주문을 생성하고 주문 번호를 반환한다.
     *
     * @param cart 장바구니
     */
    public OrderResponse createOrder() {
        List<OrderRequestItem> productQuantityInfos = cart.getItems().stream()
                .map(cartProduct -> new OrderRequestItem(cartProduct.getProduct().productNo(), cartProduct.getQuantity()))
                .toList();

        OrderRequest orderRequest = new OrderRequest(productQuantityInfos);
        String orderNo = orderService.create(orderRequest);
        PaymentResponse paymentResponse = paymentService.findOne(orderNo);
        cart.clear();

        List<String> productNoList = productQuantityInfos.stream()
                .map(OrderRequestItem::productNo)
                .toList();
        List<ProductDto> productDtoList = productService.findList(productNoList);

        return new OrderResponse(paymentResponse, productQuantityInfos, productDtoList);
    }

    /**
     * 주문 데이터를 검증한다.
     *
     * @param productNo 상둠 번호
     * @param quantity 주문 수량
     */
    public void validateOrderData(String productNo, int quantity) {
        if (productNo.isEmpty() || quantity <= 0) {
            throw new BadCommandException();
        }
    }

    /**
     * 모든 상품 목록을 가져온다.
     */
    public List<ProductDto> getAllProducts() {
        return productService.getAll();
    }

    public void clearCart() {
        this.cart.clear();
    }
}