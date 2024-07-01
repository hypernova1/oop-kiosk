package kr.co._29cm.homework.cart.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(String userId);
    Cart save(Cart cart);
}
