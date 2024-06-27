package kr.co._29cm.homework.cart.application;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.cart.domain.CartProductRepository;

import java.util.List;

public class CartService {

    private final CartProductRepository cartProductRepository;

    public CartService(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    public void addProduct(String productNo, int quantity) {
        CartProduct cartProduct = CartProduct.of(productNo, quantity);
        cartProductRepository.save(cartProduct);
    }

    public List<CartProduct> findAll() {
        return this.cartProductRepository.findAll();
    }

}
