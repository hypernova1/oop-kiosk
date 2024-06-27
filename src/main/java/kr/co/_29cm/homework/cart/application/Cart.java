package kr.co._29cm.homework.cart.application;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<CartProduct> cartProducts = new ArrayList<>();

    public List<CartProduct> getProducts() {
        return cartProducts;
    }

    public void addProduct(ProductDto productDto, int quantity) {
        this.cartProducts.add(CartProduct.of(productDto, quantity));
    }
}
