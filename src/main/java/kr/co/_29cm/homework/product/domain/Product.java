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

    protected Product(String productNo, String name, Integer price, Integer quantity) {
        this.productNo = productNo;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * 상품 번호를 가져온다.
     *
     * @return 상품 번호
     * */
    public String getProductNo() {
        return this.productNo;
    }

    /**
     * 상품 이름을 가져온다.
     *
     * @return 상품 이름
     * */
    public String getName() {
        return this.name;
    }

    /**
     * 상품 가격을 가져온다.
     *
     * @return 상품 가격
     * */
    public Integer getPrice() {
        return this.price;
    }

    /**
     * 상품의 재고를 가져온다.
     *
     * @return 상품 재고
     * */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 상품의 수량을 낮춘다.
     *
     * @param quantity 낮출 수량
     * */
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
