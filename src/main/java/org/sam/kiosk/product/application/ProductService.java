package org.sam.kiosk.product.application;

import org.sam.kiosk.common.lock.LockKeyGenerator;
import org.sam.kiosk.common.lock.LockManager;
import org.sam.kiosk.common.lock.LockType;
import org.sam.kiosk.product.domain.Product;
import org.sam.kiosk.product.domain.ProductNotFoundException;
import org.sam.kiosk.product.domain.ProductRepository;
import org.sam.kiosk.product.application.payload.ProductDto;
import org.sam.kiosk.product.application.payload.ProductPriceDto;
import org.sam.kiosk.product.application.payload.ProductQuantityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final LockManager lockManager;

    /**
     * 모든 상품을 가져온다.
     *
     * @return 모든 상품 정보
     */
    public List<ProductDto> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductDto::new).toList();
    }

    /**
     * 상품의 수량을 감소시킨다.
     *
     * @param productQuantities 상품 수량 정보
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseStock(List<ProductQuantityDto> productQuantities) {
        List<String> productNoList = productQuantities.stream()
                .map(ProductQuantityDto::productNo)
                .toList();

        List<String> lockKeys = LockKeyGenerator.generateLockKeyList(LockType.PRODUCT, productNoList);
        try {
            lockManager.acquireList(lockKeys);

            List<Product> products = this.productRepository.findByProductNoIn(productNoList);

            for (ProductQuantityDto quantityInfo : productQuantities) {
                Product product = products.stream()
                        .filter((p) -> p.getProductNo().equals(quantityInfo.productNo()))
                        .findFirst()
                        .orElseThrow(() -> new ProductNotFoundException(quantityInfo.productNo()));

                product.decreaseStock(quantityInfo.quantity());
            }
        } finally {
            lockManager.releaseList(lockKeys);
        }
    }

    /**
     * 상품의 가격 정보 목록을 가져온다.
     *
     * @param productNoList 상품 번호 목록
     * @return 상품 가격 정보 목록
     */
    public List<ProductPriceDto> getProductPrices(List<String> productNoList) {
        List<Product> products = this.productRepository.findByProductNoIn(productNoList);

        return products.stream()
                .map((product) -> new ProductPriceDto(product.getProductNo(), product.getPrice()))
                .toList();
    }

    /**
     * 상품 목록을 조회한다.
     *
     * @param productNoList 상품 번호 목록
     * @return 상품 정보 목록
     * */
    public List<ProductDto> findList(List<String> productNoList) {
        return this.productRepository.findByProductNoIn(productNoList).stream()
                .map(ProductDto::new)
                .toList();
    }

    /**
     * 상품이 존재하는지 확인한다.
     *
     * @param productNo 상품 번호
     * @return 상품
     */
    public boolean exists(String productNo) {
        return this.productRepository.existsByProductNo(productNo);
    }
}
