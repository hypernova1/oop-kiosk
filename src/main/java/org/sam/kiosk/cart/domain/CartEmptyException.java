package org.sam.kiosk.cart.domain;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("장바구니가 비어있습니다.");
    }
}
