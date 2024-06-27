package kr.co._29cm.homework.product.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void test() {
        Product product = new Product("432212", "테스트 상품", 1000, 10);
    }

}