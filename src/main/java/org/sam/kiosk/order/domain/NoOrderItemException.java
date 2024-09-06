package org.sam.kiosk.order.domain;

public class NoOrderItemException extends RuntimeException {
    public NoOrderItemException() {
        super("주문시 주문 상품은 1개 이상이어야 합니다.");
    }
}
