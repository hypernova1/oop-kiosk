package kr.co._29cm.homework.product.domain;

public class SoldOutException extends RuntimeException {
    public SoldOutException(String productNo, int stock, int quantity) {
        super("재고가 부족합니다. 상품 번호: " + productNo + ", 재고: " + stock + ",주문 수량: " + quantity);
    }
}
