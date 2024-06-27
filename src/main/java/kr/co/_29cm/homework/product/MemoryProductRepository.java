package kr.co._29cm.homework.product;

import kr.co._29cm.homework.util.CsvInstanceConvertor;

import java.util.List;

public class MemoryProductRepository implements ProductRepository {

    private final List<Product> products;

    private MemoryProductRepository() {
        products = CsvInstanceConvertor.createInstances("product.csv", Product.class);
    }

}
