package kr.co._29cm.homework.cart.domain;

import kr.co._29cm.homework.product.payload.ProductDto;

public class CartProduct {

    private ProductDto product;
    private int quantity;

    public static CartProduct of(ProductDto product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.product = product;
        cartProduct.quantity = quantity;
        return cartProduct;
    }

    public ProductDto getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
