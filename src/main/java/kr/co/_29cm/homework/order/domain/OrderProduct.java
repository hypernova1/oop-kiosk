package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;

public class OrderProduct {

    private String productNo;
    private int quantity;

    public static OrderProduct from(CartProduct cartProduct) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.productNo = cartProduct.getProductNo();
        orderProduct.quantity = cartProduct.getQuantity();
        return orderProduct;
    }
}
