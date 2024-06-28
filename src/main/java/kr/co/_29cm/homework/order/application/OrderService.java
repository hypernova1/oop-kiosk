package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.order.domain.NoOrderItemException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductQuantityInfo;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, ProductService productService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    /**
     * 주문을 생성한다.
     *
     * @param cart 장바구니
     * @return 주문 번호
     * */
    public String create(Cart cart) {
        if (cart.isEmpty()) {
            throw new NoOrderItemException();
        }

        List<ProductQuantityInfo> productQuantityInfos = cart.getProducts().stream()
                .map((cartProduct) -> new ProductQuantityInfo(cartProduct.getProduct().productNo(), cartProduct.getQuantity()))
                .collect(Collectors.toList());

        productService.decreaseStock(productQuantityInfos);
        Order order = Order.from(cart);

        this.orderRepository.save(order);
        this.paymentService.create(order);

        return order.getOrderNo();
    }

}
