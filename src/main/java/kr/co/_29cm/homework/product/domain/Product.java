package kr.co._29cm.homework.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    private String productNo;

    @Column
    private String name;

    @Column
    private int price;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    public Product(String productNo, String name, int price, Stock stock) {
        this.productNo = productNo;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * 상품의 수량을 낮춘다.
     *
     * @param quantity 낮출 수량
     * */
    public void decreaseStock(int quantity) {
        if (this.stock.getStock() < quantity) {
            throw new SoldOutException(productNo, this.stock.getStock(), quantity);
        }
        this.stock.decreaseStock(quantity);
    }

    public void rollbackStock(int quantity) {
        this.stock.rollbackStock(quantity);
    }

}
