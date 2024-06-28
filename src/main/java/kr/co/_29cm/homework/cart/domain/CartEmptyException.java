package kr.co._29cm.homework.cart.domain;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {
        super("장바구니가 비어있습니다.");
    }
}
