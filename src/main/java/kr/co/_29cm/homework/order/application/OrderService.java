package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.NoOrderItemException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.order.payload.OrderRequest;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductPriceDto;
import kr.co._29cm.homework.product.payload.ProductQuantityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;

    /**
     * 주문을 생성한다.
     *
     * @param orderRequest 주문 정보
     * @return 주문 번호
     * */
    @Transactional
    public String create(OrderRequest orderRequest) {
        if (orderRequest.products().isEmpty()) {
            throw new NoOrderItemException();
        }

        List<ProductQuantityDto> productQuantityDtoList = orderRequest.products().stream()
                .map((product) -> new ProductQuantityDto(product.productNo(), product.quantity()))
                .toList();
        productService.decreaseStock(productQuantityDtoList);

        List<String> productNoList = productQuantityDtoList.stream().map(ProductQuantityDto::productNo).toList();
        List<ProductPriceDto> productPrices = productService.getProductPrices(productNoList);

        Order order = Order.of(orderRequest, productPrices);

        this.orderRepository.save(order);
        this.paymentService.create(order);
        return order.getOrderNo();
    }

}
