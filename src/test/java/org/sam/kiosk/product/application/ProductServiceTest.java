package org.sam.kiosk.product.application;

import org.sam.kiosk.common.lock.LockManager;
import org.sam.kiosk.common.lock.MemoryLockManager;
import org.sam.kiosk.product.domain.Product;
import org.sam.kiosk.product.domain.ProductRepository;
import org.sam.kiosk.product.domain.SoldOutException;
import org.sam.kiosk.product.domain.Stock;
import org.sam.kiosk.product.application.payload.ProductQuantityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final LockManager lockManager = new MemoryLockManager();

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final static int STOCK = 10;
    private final String PRODUCT_NO = "100000";

    @BeforeEach
    void setUp() {
        this.productService = new ProductService(productRepository, lockManager);
        Product testProduct = new Product(PRODUCT_NO, "테스트 상품", 10000, new Stock(STOCK));
        when(productRepository.findByProductNoIn(List.of(testProduct.getProductNo())))
                .thenReturn(List.of(testProduct));
    }

    @Test
    @DisplayName("멀티 스레드 환경에서 SoldOutException이 정확하게 동작하는지 확인")
    void test_multi_thread_test() throws InterruptedException {
        //given
        int NUMBER_OR_THREADS = 500;
        int LOOP_COUNT = 100;

        List<ProductQuantityDto> productQuantityDtoList = List.of(new ProductQuantityDto(PRODUCT_NO, 1));

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OR_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OR_THREADS);

        AtomicInteger numberOfException = new AtomicInteger();

        //when
        for (int i = 0; i < NUMBER_OR_THREADS; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    try {
                        productService.decreaseStock(productQuantityDtoList);
                    } catch (SoldOutException e) {
                        numberOfException.getAndIncrement();
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        //then
        assertThat(NUMBER_OR_THREADS * LOOP_COUNT - STOCK).isEqualTo(numberOfException.get());
    }

}