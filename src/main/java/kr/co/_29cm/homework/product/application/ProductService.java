package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.payload.ProductDto;
import kr.co._29cm.homework.product.payload.ProductPriceInfo;
import kr.co._29cm.homework.product.payload.ProductQuantityInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public ProductDto findOne(String productNo) {
        Product product = this.productRepository.findByProductNo(productNo)
                .orElseThrow(ProductNotFoundException::new);
        return new ProductDto(product);
    }

    public void decreaseQuantity(List<ProductQuantityInfo> productQuantityInfos) {
        List<String> productNoList = productQuantityInfos.stream()
                .map(ProductQuantityInfo::productNo)
                .collect(Collectors.toList());
        List<Product> products = this.productRepository.findByProductNoList(productNoList);

        for (ProductQuantityInfo quantityCheck : productQuantityInfos) {
            Product product = products.stream()
                    .filter((p) -> p.getProductNo().equals(quantityCheck.productNo()))
                    .findFirst()
                    .orElseThrow(ProductNotFoundException::new);

            product.decreaseQuantity(quantityCheck.quantity());
        }

        productRepository.save(products);
    }

    public List<ProductPriceInfo> getProductPrices(List<String> productNoList) {
        List<Product> products = this.productRepository.findByProductNoList(productNoList);

        return products.stream()
                .map((product) -> new ProductPriceInfo(product.getProductNo(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
