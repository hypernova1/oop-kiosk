package kr.co._29cm.homework.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByProductNoIn(List<String> productNoList);

    boolean existsByProductNo(String productNo);
}
