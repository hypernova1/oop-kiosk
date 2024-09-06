package org.sam.kiosk.payment.payload;

public record PaymentRequest(String orderNo, int productPrice, int shippingPrice, String userId) {
}
