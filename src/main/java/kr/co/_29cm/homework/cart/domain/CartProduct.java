package kr.co._29cm.homework.cart.domain;

public class CartProduct {

    private String productNo;
    private int quantity;

    public static CartProduct of(String productNo, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.productNo = productNo;
        cartProduct.quantity = quantity;
        return cartProduct;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
