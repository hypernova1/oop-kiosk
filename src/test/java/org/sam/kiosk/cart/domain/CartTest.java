package org.sam.kiosk.cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    @Test
    @DisplayName("동일한 상품 추가시 수량만 증가")
    void test_increase_quantity() {
        //given
        Cart cart = new Cart();
        cart.addItem("1000", 1);
        cart.addItem("1000", 5);

        //when
        List<CartItem> items = cart.getItems();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(6);
    }

    @Test
    @DisplayName("서로 다른 상품인 경우")
    void test_add_different_product() {
        //given
        Cart cart = new Cart();
        cart.addItem("1000", 1);
        cart.addItem("1001", 5);

        //when
        List<CartItem> items = cart.getItems();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(1);
    }

}