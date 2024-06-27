package kr.co._29cm.homework.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findByProductNo(String productNo);
}
