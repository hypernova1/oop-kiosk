package kr.co._29cm.homework.cart.domain;

import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.payload.ProductDto;

public class CartProduct {

    @PrimaryKey
    private Integer id;
    private ProductDto product;
    private int quantity;

    public static CartProduct of(ProductDto product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.product = product;
        cartProduct.quantity = quantity;
        return cartProduct;
    }

    public Integer getId() {
        return this.id;
    }

    public ProductDto getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
