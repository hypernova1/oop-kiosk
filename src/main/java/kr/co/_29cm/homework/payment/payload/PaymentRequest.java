package kr.co._29cm.homework.payment.payload;

public record PaymentRequest(String orderNo, int productPrice, int shippingPrice, String userId) {
}
