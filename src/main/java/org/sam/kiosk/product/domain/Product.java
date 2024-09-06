package org.sam.kiosk.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("상품 정보")
@Table(name = "product")
@Entity
public class Product {

    @Comment("상품 번호")
    @Id
    @Column(name = "product_no", nullable = false, columnDefinition = "varchar(255)")
    private String productNo;

    @Comment("상품명")
    @Column
    private String name;

    @Comment("상품 가격")
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
     * 상품의 수량을 감소 시킨다.
     *
     * @param quantity 감소시킬 상품 수량
     * */
    public void decreaseStock(int quantity) {
        if (this.stock.getAmount() < quantity) {
            throw new SoldOutException(productNo, this.stock.getAmount(), quantity);
        }
        this.stock.decreaseStock(quantity);
    }

    /**
     * 상품의 재고 수량을 증가시킨다.
     *
     * @param quantity 증가시킬 상품 수량
     * */
    public void increaseStock(int quantity) {
        this.stock.increaseStock(quantity);
    }

}
