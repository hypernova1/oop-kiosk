package kr.co._29cm.homework.cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    @Test
    @DisplayName("동일한 상품 추가시 수량만 증가")
    void test_increase_quantity() {
        Cart cart = new Cart();
        cart.addItem("1000", 1);
        cart.addItem("1000", 5);

        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(6);
    }

    @Test
    @DisplayName("서로 다른 상품인 경우")
    void test_add_different_product() {
        Cart cart = new Cart();
        cart.addItem("1000", 1);
        cart.addItem("1001", 5);

        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(1);
    }

}