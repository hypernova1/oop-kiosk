package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;

public class OrderItem {

    private String productNo;
    private int quantity;

    public static OrderItem from(CartProduct cartProduct) {
        OrderItem orderItem = new OrderItem();
        orderItem.productNo = cartProduct.getProductNo();
        orderItem.quantity = cartProduct.getQuantity();
        return orderItem;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
