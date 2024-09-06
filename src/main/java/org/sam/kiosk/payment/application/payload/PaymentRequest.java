package org.sam.kiosk.payment.application.payload;

public record PaymentRequest(String orderNo, int productPrice, int shippingPrice, String userId) {
}
