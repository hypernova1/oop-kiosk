package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.common.lock.MemoryLockManager;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductQuantityInfo;
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
import java.util.stream.Collectors;

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
        int NUMBER_OR_THREADS = 2000;

        CsvData csvData = new CsvDataReader("product.csv").readCsv();
        List<Product> dummyProducts = new CsvToInstanceConvertor<>(csvData, Product.class).convertToInstances();
        Product dummyProduct = dummyProducts.get(0);
        int stock = dummyProduct.getStock();

        List<ProductQuantityInfo> quantityInfos = new ArrayList<>();
        ProductQuantityInfo quantityInfo = new ProductQuantityInfo(dummyProduct.getProductNo(), 1);
        quantityInfos.add(quantityInfo);

        List<String> productNoList = quantityInfos.stream().map(ProductQuantityInfo::productNo).collect(Collectors.toList());

        when(productRepository.findByProductNoList(productNoList)).thenReturn(dummyProducts);

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OR_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OR_THREADS);

        AtomicInteger numberOfException = new AtomicInteger();
        for (int i = 0; i < NUMBER_OR_THREADS; i++) {
            executorService.execute(() -> {
                try {
                    productService.decreaseStock(quantityInfos);
                } catch (SoldOutException e) {
                    numberOfException.getAndIncrement();
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        assertThat(NUMBER_OR_THREADS - stock).isEqualTo(numberOfException.get());
    }

}