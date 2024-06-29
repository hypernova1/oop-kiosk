package kr.co._29cm.homework.product.payload;

import kr.co._29cm.homework.product.domain.Product;

public record ProductDto(String productNo, String name, int price, int quantity) {
    public ProductDto(Product product) {
        this(product.getProductNo(), product.getName(), product.getPrice(), product.getStock().getStock());
    }
}
