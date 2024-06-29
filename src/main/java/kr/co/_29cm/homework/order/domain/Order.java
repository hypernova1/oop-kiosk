package kr.co._29cm.homework.order.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.order.payload.OrderRequest;
import kr.co._29cm.homework.order.payload.OrderRequestItem;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.payload.ProductPriceDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    private String orderNo;

    @OneToMany(mappedBy = "order")
    private final List<OrderItem> items = new ArrayList<>();

    @Column
    private String userId;

    private static final int FREE_SHIPPING_THRESHOLD = 50_000;
    private static final int SHIPPING_PRICE = 2500;

    public static Order of(OrderRequest orderRequest, List<ProductPriceDto> productPriceDtos) {
        Order order = new Order();
        order.orderNo = generateOrderNo();
        order.setOrderItems(orderRequest.products(), productPriceDtos);
        order.userId = orderRequest.userId();
        return order;
    }

    /**
     * 장바구니 상품 목록을 기반으로 주문 아이템 목록을 생성한다.
     *
     * @param orderRequestItems 장바구니 상품 목록
     * */
    private void setOrderItems(List<OrderRequestItem> orderRequestItems, List<ProductPriceDto> productPriceDtos) {
        for (OrderRequestItem orderRequestItem : orderRequestItems) {
            ProductPriceDto productPriceDto = productPriceDtos.stream()
                    .filter((pi) -> pi.productNo().equals(orderRequestItem.productNo()))
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException(orderRequestItem.productNo()));
            OrderItem orderItem = OrderItem.of(orderRequestItem, productPriceDto.price());
            this.items.add(orderItem);
        }
    }

    /**
     * 무료 배송 조건이 맞는지 확인한다.
     * */
    public boolean isFreeShipping() {
        return getTotalProductPrice() >= FREE_SHIPPING_THRESHOLD;
    }

    /**
     * 총 상품 금액을 가져온다.
     * */
    public int getTotalProductPrice() {
        return this.items.stream().mapToInt(OrderItem::getTotalProductPrice).sum();
    }

    public int getShippingPrice() {
        if (isFreeShipping()) {
            return 0;
        }
        return SHIPPING_PRICE;
    }

    /**
     * 주문 번호를 생성한다.
     * */
    static String generateOrderNo() {
        return DateTimeFormatter.ofPattern("YYYYMMddHHmmss").format(LocalDateTime.now());
    }

}
