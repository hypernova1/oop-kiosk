package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.common.repository.DefaultMemoryRepository;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.util.csv.CsvData;
import kr.co._29cm.homework.util.csv.CsvDataReader;
import kr.co._29cm.homework.util.csv.CsvToInstanceConvertor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryProductRepository extends DefaultMemoryRepository<Product, String> implements ProductRepository {

    public MemoryProductRepository() {
        CsvData csvData = new CsvDataReader("product.csv").readCsv();
        items = new CsvToInstanceConvertor<>(csvData, Product.class).convertToInstances();
    }

    @Override
    public Optional<Product> findByProductNo(String productNo) {
        return this.findByPrimaryKey(productNo);
    }

    @Override
    public List<Product> findByProductNoList(List<String> productNoList) {
        return this.items.stream()
                .filter((product) -> productNoList.contains(product.getProductNo()))
                .toList();
    }

}
