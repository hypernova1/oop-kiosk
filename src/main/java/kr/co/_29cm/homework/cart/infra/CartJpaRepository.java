package kr.co._29cm.homework.cart.infra;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.cart.domain.CartRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, String>, CartRepository {
}
