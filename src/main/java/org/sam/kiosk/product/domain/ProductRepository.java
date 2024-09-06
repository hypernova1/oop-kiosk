package org.sam.kiosk.product.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByProductNoIn(List<String> productNoList);

    boolean existsByProductNo(String productNo);
}
