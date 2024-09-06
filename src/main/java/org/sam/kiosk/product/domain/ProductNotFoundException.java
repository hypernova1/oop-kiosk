package org.sam.kiosk.product.domain;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productNo) {
        super("상품을 찾을 수 없습니다. 상품 번호: " + productNo);
    }
}
