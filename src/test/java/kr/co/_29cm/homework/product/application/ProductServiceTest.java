package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.domain.Stock;
import kr.co._29cm.homework.product.payload.ProductQuantityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private LockManager lockManager;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository, lockManager);
    }

    @Test
    @DisplayName("멀티 스레드 환경에서 SoldOutException이 정확하게 동작하는지 확인")
    void test_multi_thread_test() throws InterruptedException {
        int NUMBER_OR_THREADS = 500;
        int LOOP_COUNT = 100;

        int stock = 50;

        Product product = new Product("10000", "테스트 상품", 10_000, new Stock(stock));

        List<ProductQuantityDto> productQuantityDtoList = new ArrayList<>();
        ProductQuantityDto productQuantityDto = new ProductQuantityDto(product.getProductNo(), 1);
        productQuantityDtoList.add(productQuantityDto);

        List<String> productNoList = productQuantityDtoList.stream().map(ProductQuantityDto::productNo).toList();
        List<Product> products = List.of(product);

        when(productRepository.findByProductNoIn(productNoList)).thenReturn(products);

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OR_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OR_THREADS);

        AtomicInteger numberOfException = new AtomicInteger();
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

        assertThat(NUMBER_OR_THREADS * LOOP_COUNT - stock).isEqualTo(numberOfException.get());
    }

}