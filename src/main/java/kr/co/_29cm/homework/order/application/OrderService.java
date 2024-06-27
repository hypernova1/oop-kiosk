package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.order.domain.NoOrderItemException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.payment.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductPriceInfo;
import kr.co._29cm.homework.product.payload.ProductQuantityInfo;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private static final int FREE_SHIPPING = 50_000;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, ProductService productService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    public void create(List<CartProduct> cartProducts) {
        if (cartProducts.isEmpty()) {
            throw new NoOrderItemException();
        }

        List<ProductQuantityInfo> productQuantityInfos = cartProducts.stream().map((cartProduct) -> new ProductQuantityInfo(cartProduct.getProductNo(), cartProduct.getQuantity()))
                .collect(Collectors.toList());

        productService.decreaseQuantity(productQuantityInfos);
        Order order = Order.from(cartProducts);

        this.orderRepository.save(order);
        this.paymentService.create(order);
    }

}
