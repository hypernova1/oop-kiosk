package kr.co._29cm.homework.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kr.co._29cm.homework.common.BaseUuidEntity;
import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderItem;
import kr.co._29cm.homework.product.payload.ProductPriceDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseUuidEntity {

    @Column
    private int productPrice;

    @Column
    private int shippingPrice;

    @Column
    private int totalPaidPrice;

    @Column
    private String orderNo;

    @Column
    private String userId;

    /**
     * 주문 데이터를 기반으로 결제 정보를 생성한다.
     *
     * @param order 주문 데이터
     * @return 결제 정보
     * */
    public static Payment from(Order order) {
        Payment payment = new Payment();
        payment.orderNo = order.getOrderNo();
        payment.productPrice = order.getTotalProductPrice();
        payment.shippingPrice = order.getShippingPrice();
        payment.totalPaidPrice = payment.productPrice + payment.shippingPrice;
        payment.orderNo = order.getOrderNo();
        payment.userId = order.getUserId();
        return payment;
    }

}
