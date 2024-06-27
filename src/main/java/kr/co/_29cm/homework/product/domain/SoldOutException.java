package kr.co._29cm.homework.product.domain;

public class SoldOutException extends RuntimeException {
    public SoldOutException() {
        super("재고가 부족합니다.");
    }
}
