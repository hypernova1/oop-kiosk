package kr.co._29cm.homework.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co._29cm.homework.common.BaseUuidEntity;
import kr.co._29cm.homework.order.payload.OrderRequestItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Comment("주문 상품")
@Entity
public class OrderItem extends BaseUuidEntity {

    @Comment("상품 번호")
    @Column(name = "product_no", nullable = false, columnDefinition = "varchar(255)")
    private String productNo;

    @Comment("상품 가격")
    @Column(name = "product_price", nullable = false, columnDefinition = "int")
    private int productPrice;

    @Comment("주문 수량")
    @Column(name = "quantity", nullable = false, columnDefinition = "int")
    private int quantity;

    @ManyToOne
    private Order order;

    /**
     * 장바구니 상품 정보로 주문 아이템을 생성한다.
     *
     * @param orderRequestItem 장바구니 상품 정보
     * @return 주문 아이템
     * */
    public static OrderItem of(OrderRequestItem orderRequestItem, int price) {
        OrderItem orderItem = new OrderItem();
        orderItem.productNo = orderRequestItem.productNo();
        orderItem.quantity = orderRequestItem.quantity();
        orderItem.productPrice = price;
        return orderItem;
    }

    /**
     * 총 상품 가격을 가져온다.
     *
     * @return 총 상품 가격
     * */
    public int getTotalProductPrice() {
        return this.productPrice * this.quantity;
    }
}
