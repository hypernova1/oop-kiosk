package kr.co._29cm.homework.product.domain;

import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.util.csv.CsvFieldMatcher;

import java.util.Objects;

public class Product {

    @PrimaryKey
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

    public void decreaseQuantity(int quantity) {
        if (this.quantity < quantity) {
            throw new SoldOutException();
        }
        this.quantity -= quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.productNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(this.productNo, product.getProductNo());
    }
}
