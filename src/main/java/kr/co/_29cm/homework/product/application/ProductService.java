package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Product findOne(String productNo) {
        Product product = this.productRepository.findByProductNo(productNo)
                .orElseThrow(ProductNotFoundException::new);
        return product;
    }

}
