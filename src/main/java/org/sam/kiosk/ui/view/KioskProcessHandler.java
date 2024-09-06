package org.sam.kiosk.ui.view;

import org.sam.kiosk.cart.application.CartService;
import org.sam.kiosk.cart.application.payload.CartItemDto;
import org.sam.kiosk.order.application.OrderService;
import org.sam.kiosk.order.application.payload.OrderRequest;
import org.sam.kiosk.order.application.payload.OrderRequestItem;
import org.sam.kiosk.order.application.payload.OrderResponse;
import org.sam.kiosk.payment.application.PaymentService;
import org.sam.kiosk.payment.application.payload.PaymentResponse;
import org.sam.kiosk.product.application.ProductService;
import org.sam.kiosk.product.domain.ProductNotFoundException;
import org.sam.kiosk.product.application.payload.ProductDto;
import org.sam.kiosk.ui.input.command.BadCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 주문 프로세스를 담당하는 핸들러
 *
 * @see Kiosk
 * */
@Service
@RequiredArgsConstructor
public class KioskProcessHandler {

    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final CartService  cartService;


    /**
     * 장바구니에 상품을 추가한다.
     *
     * @param userId 유저 아이디
     * @param productNo 상품
     * @param quantity 구매할 수량
     */
    public void addProductToCart(String userId, String productNo, int quantity) {
        boolean existsProduct = this.productService.exists(productNo);
        if (!existsProduct) {
            throw new ProductNotFoundException(productNo);
        }
        validateOrderData(productNo, quantity);
        cartService.addItem(userId, productNo, quantity);
    }

    /**
     * 주문을 생성하고 주문 정보를 반환한다.
     *
     * @param userId 유저 아이디
     * @return 주문 정보
     */
    public OrderResponse createOrder(String userId) {
        List<CartItemDto> cartItems = this.cartService.findItems(userId);

        List<OrderRequestItem> orderRequestItems = cartItems.stream()
                .map(cartItem -> new OrderRequestItem(cartItem.productNo(), cartItem.quantity()))
                .toList();
        OrderRequest orderRequest = new OrderRequest(orderRequestItems, userId);

        String orderNo = orderService.create(orderRequest);

        PaymentResponse paymentResponse = paymentService.findOne(orderNo);

        this.cartService.clearCart(userId);

        List<String> productNoList = orderRequestItems.stream()
                .map(OrderRequestItem::productNo)
                .toList();
        List<ProductDto> productDtoList = productService.findList(productNoList);

        return new OrderResponse(paymentResponse, orderRequestItems, productDtoList);
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

    /**
     * 장바구니를 비운다.
     *
     * @param userId 유저 아이디
     * */
    public void clearCart(String userId) {
        this.cartService.clearCart(userId);
    }

    /**
     * 장바구니에 상품이 존재하는지 확인한다.
     *
     * @param userId 유저 아이디
     * @return 장바구니 상품 존재 여부
     * */
    public boolean existCartItems(String userId) {
        return this.cartService.existsCartItems(userId);
    }
}