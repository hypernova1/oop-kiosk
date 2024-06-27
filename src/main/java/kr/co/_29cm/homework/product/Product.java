package kr.co._29cm.homework.product;

import kr.co._29cm.homework.util.CsvMatcher;

public class Product {

    @CsvMatcher("상품번호")
    private String productNo;

    @CsvMatcher("상품명")
    private String name;

    @CsvMatcher("판매가격")
    private Integer price;

    @CsvMatcher("재고수량")
    private Integer quantity;


    protected Product() {

    }

}
