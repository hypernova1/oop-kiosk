package kr.co._29cm.homework.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kr.co._29cm.homework.common.BaseUuidEntity;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.payment.payload.PaymentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
