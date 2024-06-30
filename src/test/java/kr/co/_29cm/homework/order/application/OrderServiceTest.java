package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.order.payload.OrderRequest;
import kr.co._29cm.homework.order.payload.OrderRequestItem;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.application.StockRollbackEvent;
import kr.co._29cm.homework.product.payload.ProductPriceDto;
import kr.co._29cm.homework.product.payload.ProductQuantityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void init() {
        this.orderService = new OrderService(orderRepository, productService, paymentService, eventPublisher);
    }


    @DisplayName("주문 생성 테스트")
    @Test
    void complete_order() {

        //given
        String userId = UUID.randomUUID().toString();
        String productNo = "768848";
        List<ProductQuantityDto> productQuantityDtoList = List.of(
                new ProductQuantityDto(productNo, 1)
        );

        List<String> productNoList = List.of(productNo);
        List<ProductPriceDto> productPrices = List.of(
                new ProductPriceDto(productNo, 19000)
        );
        OrderRequest orderRequest = new OrderRequest(List.of(new OrderRequestItem(productNo, 1)), userId);
        when(productService.getProductPrices(productNoList)).thenReturn(productPrices);

        //when
        String orderNo = orderService.create(orderRequest);

        //then
        assertThat(orderNo).isNotNull();
        verify(productService, times(1)).decreaseStock(productQuantityDtoList);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @DisplayName("주문 실패시 재고 롤백 테스트")
    @Test
    void test_rollback_stock() {

        //given
        String userId = UUID.randomUUID().toString();
        String productNo = "768848";
        OrderRequest orderRequest = new OrderRequest(List.of(new OrderRequestItem(productNo, 1)), userId);
        List<ProductQuantityDto> productQuantityDtoList = List.of(
                new ProductQuantityDto(productNo, 1)
        );

        List<String> productNoList = List.of(productNo);
        List<ProductPriceDto> productPrices = List.of(
                new ProductPriceDto(productNo, 19000)
        );

        when(productService.getProductPrices(productNoList)).thenReturn(productPrices);
        doThrow(new RuntimeException()).when(paymentService).create(any());

        //when
        try {
            this.orderService.create(orderRequest);
        } catch (RuntimeException e) {}

        //then
        verify(productService, times(1)).decreaseStock(productQuantityDtoList);
        verify(eventPublisher, times(1)).publishEvent(new StockRollbackEvent(productQuantityDtoList));
    }


}