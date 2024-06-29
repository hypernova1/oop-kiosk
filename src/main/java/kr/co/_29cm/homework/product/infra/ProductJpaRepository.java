package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String>, ProductRepository {
}
