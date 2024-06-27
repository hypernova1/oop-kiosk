package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockKeyGenerator;
import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.common.lock.LockType;
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
    private final LockManager lockManager;

    public ProductService(ProductRepository productRepository, LockManager lockManager) {
        this.productRepository = productRepository;
        this.lockManager = lockManager;
    }

    /**
     * 모든 상품을 가져온다.
     *
     * @return 모든 상품 정보
     * */
    public List<ProductDto> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    /**
     * 상품 번호로 상품을 조회한다.
     *
     * @return 상품
     * */
    public ProductDto findOne(String productNo) {
        Product product = this.productRepository.findByProductNo(productNo)
                .orElseThrow(() -> new ProductNotFoundException(productNo));
        return new ProductDto(product);
    }

    /**
     * 상품의 수량을 뺀다.
     *
     * @param productQuantityInfos 상품 수량 정보
     * */
    public void decreaseQuantity(List<ProductQuantityInfo> productQuantityInfos) {
        List<String> productNoList = productQuantityInfos.stream()
                .map(ProductQuantityInfo::productNo)
                .collect(Collectors.toList());

        List<String> lockKeys = LockKeyGenerator.generateLockKeyList(LockType.PRODUCT, productNoList);
        try {
            lockManager.acquireList(lockKeys);

            List<Product> products = this.productRepository.findByProductNoList(productNoList);

            for (ProductQuantityInfo quantityInfo : productQuantityInfos) {
                //TODO: 실제로 메모리에서 수량을 빼는 거라 롤백 전략 필요함
                Product product = products.stream()
                        .filter((p) -> p.getProductNo().equals(quantityInfo.productNo()))
                        .findFirst()
                        .orElseThrow(() -> new ProductNotFoundException(quantityInfo.productNo()));

                product.decreaseQuantity(quantityInfo.quantity());
            }

            productRepository.save(products);
        } finally {
            lockManager.releaseList(lockKeys);
        }
    }

    /**
     * 상품의 가격 정보 목록을 가져온다.
     *
     * @param productNoList 상품 번호 목록
     * @return 상품 가격 정보 목록
     * */
    public List<ProductPriceInfo> getProductPrices(List<String> productNoList) {
        List<Product> products = this.productRepository.findByProductNoList(productNoList);

        return products.stream()
                .map((product) -> new ProductPriceInfo(product.getProductNo(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
