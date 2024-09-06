package org.sam.kiosk.order.domain;

import jakarta.persistence.*;
import org.sam.kiosk.order.application.payload.OrderRequest;
import org.sam.kiosk.order.application.payload.OrderRequestItem;
import org.sam.kiosk.product.domain.ProductNotFoundException;
import org.sam.kiosk.product.application.payload.ProductPriceDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Comment("주문 정보")
@Table(name = "`order`", indexes = @Index(name = "idx_order_user_id", columnList = "user_id"))
public class Order {

    @Id
    @Comment("주문 번호")
    @Column(name = "order_no", nullable = false, columnDefinition = "char(14)")
    private String orderNo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> items = new ArrayList<>();

    @Comment("주문자 유저 아이디")
    @Column(name = "user_id", nullable = false, columnDefinition = "varchar(255)")
    private String userId;

    /**
     * 주문을 생성한다.
     *
     * @param orderRequest 주문 데이터
     * @param productPrices 상품 가격 정보
     * @return 주문
     * */
    public static Order of(OrderRequest orderRequest, List<ProductPriceDto> productPrices) {
        Order order = new Order();
        order.orderNo = generateOrderNo();
        order.setOrderItems(orderRequest.products(), productPrices);
        order.userId = orderRequest.userId();
        return order;
    }

    /**
     * 장바구니 상품 목록을 기반으로 주문 아이템 목록을 생성한다.
     *
     * @param orderRequestItems 장바구니 상품 목록
     * @param productPrices 상품 가격 정보
     * */
    private void setOrderItems(List<OrderRequestItem> orderRequestItems, List<ProductPriceDto> productPrices) {
        for (OrderRequestItem orderRequestItem : orderRequestItems) {
            ProductPriceDto productPriceDto = productPrices.stream()
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
        return getTotalProductPrice() >= OrderPolicy.FREE_SHIPPING_THRESHOLD;
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
        return ShippingPolicy.SHIPPING_PRICE;
    }

    /**
     * 주문 번호를 생성한다.
     * */
    static String generateOrderNo() {
        return DateTimeFormatter.ofPattern("YYYYMMddHHmmss").format(LocalDateTime.now());
    }

}
