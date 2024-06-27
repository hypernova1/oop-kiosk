package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductQuantityCheckDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public void checkQuantity(List<ProductQuantityCheckDto> productQuantityCheckDtos) {
        List<String> productNoList = productQuantityCheckDtos.stream()
                .map(ProductQuantityCheckDto::productNo)
                .collect(Collectors.toList());
        List<Product> products = this.productRepository.findByProductNoList(productNoList);

        for (ProductQuantityCheckDto quantityCheck : productQuantityCheckDtos) {
            Product product = products.stream()
                    .filter((p) -> p.getProductNo().equals(quantityCheck.productNo()))
                    .findFirst()
                    .orElseThrow(ProductNotFoundException::new);

            if (product.getQuantity() < quantityCheck.quantity()) {
                throw new SoldOutException();
            }
        }

    }
}
