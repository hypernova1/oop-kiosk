package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.common.lock.LockManager;
import kr.co._29cm.homework.common.lock.MemoryLockManager;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.util.csv.CsvData;
import kr.co._29cm.homework.util.csv.CsvDataReader;
import kr.co._29cm.homework.util.csv.CsvToInstanceConvertor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private final LockManager lockManager = new MemoryLockManager();

    @InjectMocks
    private final ProductService service = new ProductService(productRepository, lockManager);

    @Test
    @DisplayName("멀티 스레드 환경에서 SoldOutException이 정확하게 동작하는지 확인")
    void test_multi_thread_test() {
        CsvData csvData = new CsvDataReader("product.csv").readCsv();
        List<Product> products = new CsvToInstanceConvertor<>(csvData, Product.class).convertToInstances();

    }

}