package kr.co._29cm.homework.cart.infra;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.cart.domain.CartProductRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryCartProductRepository implements CartProductRepository {

    private List<CartProduct> cartProducts = new ArrayList<>();

    @Override
    public List<CartProduct> findAll() {
        return this.cartProducts;
    }

    @Override
    public void save(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
    }

    @Override
    public void clear() {
        this.cartProducts.clear();
    }


}
