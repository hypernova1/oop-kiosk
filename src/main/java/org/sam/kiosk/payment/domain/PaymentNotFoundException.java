package org.sam.kiosk.payment.domain;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String orderNo) {
        super("주문 정보를 찾을 수 없습니다. 주문번호: " + orderNo);
    }

}
