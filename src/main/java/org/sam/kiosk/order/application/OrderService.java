package org.sam.kiosk.order.application;

import org.sam.kiosk.order.domain.NoOrderItemException;
import org.sam.kiosk.order.domain.Order;
import org.sam.kiosk.order.domain.OrderRepository;
import org.sam.kiosk.order.payload.OrderRequest;
import org.sam.kiosk.order.payload.OrderRequestItem;
import org.sam.kiosk.payment.application.PaymentService;
import org.sam.kiosk.payment.payload.PaymentRequest;
import org.sam.kiosk.product.application.ProductService;
import org.sam.kiosk.product.application.StockRollbackEvent;
import org.sam.kiosk.product.payload.ProductPriceDto;
import org.sam.kiosk.product.payload.ProductQuantityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 주문을 생성한다.
     *
     * @param orderRequest 주문 정보
     * @return 주문 번호
     */
    @Transactional
    public String create(OrderRequest orderRequest) {
        if (orderRequest.products().isEmpty()) {
            throw new NoOrderItemException();
        }

        List<ProductQuantityDto> productQuantityDtoList = orderRequest.products().stream()
                .map((product) -> new ProductQuantityDto(product.productNo(), product.quantity()))
                .toList();
        productService.decreaseStock(productQuantityDtoList);

        try {
            return createOrder(orderRequest);
        } catch (RuntimeException e) {
            this.eventPublisher.publishEvent(new StockRollbackEvent(productQuantityDtoList));
            throw e;
        }
    }

    /**
     * 주문을 생성한다.
     *
     * @param orderRequest 주문 요청 데이터
     * @return 주문 번호
     * */
    private String createOrder(OrderRequest orderRequest) {
        List<String> productNoList = orderRequest.products().stream().map(OrderRequestItem::productNo).toList();
        List<ProductPriceDto> productPrices = productService.getProductPrices(productNoList);

        Order order = Order.of(orderRequest, productPrices);

        this.orderRepository.save(order);
        this.paymentService.create(
                new PaymentRequest(
                        order.getOrderNo(),
                        order.getTotalProductPrice(),
                        order.getShippingPrice(),
                        order.getUserId()
                )
        );
        return order.getOrderNo();
    }

}
