package kr.co._29cm.homework.payment.payload;

import kr.co._29cm.homework.payment.domain.Payment;

public record PaymentDto(int productPrice, int shippingPrice, int totalPaidPrice) {
    public PaymentDto(Payment payment) {
        this(payment.getProductPrice(), payment.getShippingPrice(), payment.getTotalPaidPrice());
    }
}
