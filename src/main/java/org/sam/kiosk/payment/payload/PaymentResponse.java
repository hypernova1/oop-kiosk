package org.sam.kiosk.payment.payload;

import org.sam.kiosk.payment.domain.Payment;

public record PaymentResponse(int productPrice, int shippingPrice, int totalPaidPrice) {
    public PaymentResponse(Payment payment) {
        this(payment.getProductPrice(), payment.getShippingPrice(), payment.getTotalPaidPrice());
    }
}
