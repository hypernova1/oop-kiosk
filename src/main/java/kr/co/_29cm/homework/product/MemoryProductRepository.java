package kr.co._29cm.homework.product;

import kr.co._29cm.homework.util.csv.CsvProcessor;

import java.util.List;

public class MemoryProductRepository implements ProductRepository {

    private final List<Product> products;

    private MemoryProductRepository() {
        products = CsvProcessor.convertToInstanceFromPath("product.csv", Product.class);
    }

}
