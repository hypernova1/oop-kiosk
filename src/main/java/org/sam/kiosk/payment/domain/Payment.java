package org.sam.kiosk.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.sam.kiosk.common.BaseUuidEntity;
import org.sam.kiosk.payment.payload.PaymentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("결제 정보")
@Table(name = "payment", indexes = @Index(name = "idx_payment_user_id", columnList = "user_id"))
@Entity
public class Payment extends BaseUuidEntity {

    @Comment("상품 가격")
    @Column(name = "product_price", nullable = false, columnDefinition = "int")
    private int productPrice;

    @Comment("배송비")
    @Column(name = "shipping_price", nullable = false, columnDefinition = "int")
    private int shippingPrice;

    @Comment("결제 금액")
    @Column(name = "total_paid_price", nullable = false, columnDefinition = "int")
    private int totalPaidPrice;

    @Comment("주문 번호")
    @Column(name = "order_no", nullable = false, columnDefinition = "varchar(14)")
    private String orderNo;

    @Comment("유저 아이디")
    @Column(name = "user_id", nullable = false, columnDefinition = "varchar(255)")
    private String userId;

    /**
     * 주문 데이터를 기반으로 결제 정보를 생성한다.
     *
     * @param paymentRequest 주문 데이터
     * @return 결제 정보
     * */
    public static Payment from(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.orderNo = paymentRequest.orderNo();
        payment.productPrice = paymentRequest.productPrice();
        payment.shippingPrice = paymentRequest.shippingPrice();
        payment.totalPaidPrice = payment.productPrice + payment.shippingPrice;
        payment.userId = paymentRequest.userId();
        return payment;
    }

}
