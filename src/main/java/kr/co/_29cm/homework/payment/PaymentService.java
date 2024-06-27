package kr.co._29cm.homework.payment;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderItem;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductPriceInfo;
import kr.co._29cm.homework.product.payload.ProductQuantityInfo;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;

    public PaymentService(PaymentRepository paymentRepository, ProductService productService) {
        this.paymentRepository = paymentRepository;
        this.productService = productService;
    }

    public void create(Order order) {
        List<String> productNoList = order.getOrderItems().stream()
                .map(OrderItem::getProductNo)
                .collect(Collectors.toList());
        List<ProductPriceInfo> productPrices = productService.getProductPrices(productNoList);
        Payment payment = Payment.from(order, productPrices);
        this.paymentRepository.save(payment);
    }

}
