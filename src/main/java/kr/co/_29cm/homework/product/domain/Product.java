package kr.co._29cm.homework.product.domain;

import kr.co._29cm.homework.util.csv.CsvFieldMatcher;

public class Product {

    @CsvFieldMatcher("상품번호")
    private String productNo;

    @CsvFieldMatcher("상품명")
    private String name;

    @CsvFieldMatcher("판매가격")
    private Integer price;

    @CsvFieldMatcher("재고수량")
    private Integer quantity;

    protected Product() {}

    public String getProductNo() {
        return this.productNo;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

}
