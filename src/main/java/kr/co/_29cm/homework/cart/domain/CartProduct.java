package kr.co._29cm.homework.cart.domain;

import kr.co._29cm.homework.common.repository.PrimaryKey;

public class CartProduct {

    @PrimaryKey
    private Integer id;
    private String productNo;
    private int quantity;

    public static CartProduct of(String productNo, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.productNo = productNo;
        cartProduct.quantity = quantity;
        return cartProduct;
    }

    public Integer getId() {
        return this.id;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
