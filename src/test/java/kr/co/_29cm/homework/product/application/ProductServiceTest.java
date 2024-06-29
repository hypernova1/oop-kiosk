package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.common.lock.MemoryLockManager;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductQuantityDto;
import kr.co._29cm.homework.util.csv.CsvData;
import kr.co._29cm.homework.util.csv.CsvDataReader;
import kr.co._29cm.homework.util.csv.CsvToInstanceConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private final LockManager lockManager = new MemoryLockManager();

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

        CsvData csvData = new CsvDataReader("product.csv").readCsv();
        List<Product> dummyProducts = new CsvToInstanceConvertor<>(csvData, Product.class).convertToInstances();
        Product dummyProduct = dummyProducts.get(0);
        int stock = dummyProduct.getStock();

        List<ProductQuantityDto> productQuantityDtoList = new ArrayList<>();
        ProductQuantityDto productQuantityDto = new ProductQuantityDto(dummyProduct.getProductNo(), 1);
        productQuantityDtoList.add(productQuantityDto);

        List<String> productNoList = productQuantityDtoList.stream().map(ProductQuantityDto::productNo).toList();

        when(productRepository.findByProductNoIn(productNoList)).thenReturn(dummyProducts);

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