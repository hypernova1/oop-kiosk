package org.sam.kiosk.product.infra;

import org.sam.kiosk.product.domain.Product;
import org.sam.kiosk.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String>, ProductRepository {
}
