package org.sam.kiosk.cart.infra;

import org.sam.kiosk.cart.domain.Cart;
import org.sam.kiosk.cart.domain.CartRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, String>, CartRepository {
}
