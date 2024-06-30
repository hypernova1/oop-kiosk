package kr.co._29cm.homework.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductTest {

    @DisplayName("수량이 0 미만일시")
    @Test
    void test() {
        Product product = new Product("1000", "test", 1000, new Stock(1));
        assertThatExceptionOfType(SoldOutException.class)
                .isThrownBy(() -> product.decreaseStock(2));
    }

}