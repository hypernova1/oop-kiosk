package kr.co._29cm.homework.cart.domain;

import java.util.List;

public interface CartProductRepository {

    List<CartProduct> findAll();

    void save(CartProduct cartProduct);

    void clear();
}
