package org.sam.kiosk.product.adapter.out;

import org.sam.kiosk.product.domain.Product;
import org.sam.kiosk.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String>, ProductRepository {
}
