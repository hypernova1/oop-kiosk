package kr.co._29cm.homework.payment.payload;

import kr.co._29cm.homework.payment.domain.Payment;

public record PaymentResponse(int productPrice, int shippingPrice, int totalPaidPrice) {
    public PaymentResponse(Payment payment) {
        this(payment.getProductPrice(), payment.getShippingPrice(), payment.getTotalPaidPrice());
    }
}
