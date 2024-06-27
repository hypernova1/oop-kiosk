package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.util.csv.CsvData;
import kr.co._29cm.homework.util.csv.CsvDataReader;
import kr.co._29cm.homework.util.csv.CsvToInstanceConvertor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryProductRepository implements ProductRepository {

    private final List<Product> products;

    public MemoryProductRepository() {
        CsvData csvData = new CsvDataReader("product.csv").readCsv();
        products = new CsvToInstanceConvertor<>(csvData, Product.class).convertToInstances();
    }

    public List<Product> findAll() {
        return this.products;
    }

    @Override
    public Optional<Product> findByProductNo(String productNo) {
        return products.stream().filter((product) -> product.getProductNo().equals(productNo)).findFirst();
    }

    @Override
    public List<Product> findByProductNoList(List<String> productNoList) {
        return this.products.stream()
                .filter((product) -> productNoList.contains(product.getProductNo()))
                .collect(Collectors.toList());
    }

}
