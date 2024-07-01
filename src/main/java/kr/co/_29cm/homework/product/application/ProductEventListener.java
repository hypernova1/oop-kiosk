package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockKeyGenerator;
import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.common.lock.LockType;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.payload.ProductQuantityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final LockManager lockManager;
    private final ProductRepository productRepository;

    /**
     * 재고를 롤백한다.
     *
     * @param event 재고 롤백 이벤트
     * */
    @Async
    @EventListener
    public void rollbackStock(StockRollbackEvent event) {
        log.error("rollback stock");
        List<String> productNoList = event.productQuantities().stream()
                .map(ProductQuantityDto::productNo)
                .toList();

        List<String> lockKeys = LockKeyGenerator.generateLockKeyList(LockType.PRODUCT, productNoList);
        try {
            lockManager.acquireList(lockKeys);

            List<Product> products = this.productRepository.findByProductNoIn(productNoList);

            for (ProductQuantityDto quantityInfo : event.productQuantities()) {
                Product product = products.stream()
                        .filter((p) -> p.getProductNo().equals(quantityInfo.productNo()))
                        .findFirst()
                        .orElseThrow(() -> new ProductNotFoundException(quantityInfo.productNo()));

                product.increaseStock(quantityInfo.quantity());
            }
        } finally {
            lockManager.releaseList(lockKeys);
        }
    }

}
