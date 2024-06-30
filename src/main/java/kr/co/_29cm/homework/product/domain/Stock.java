package kr.co._29cm.homework.product.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.common.BaseUuidEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("상품 재고")
@Table(name = "stock", indexes = @Index(name = "idx_product_stock_product_no", columnList = "product_no"))
@Entity
public class Stock extends BaseUuidEntity {

    @Comment("수량")
    @Column(name = "amount", nullable = false, columnDefinition = "int")
    private int amount;

    @OneToOne
    @JoinColumn(name = "product_no", referencedColumnName = "product_no")
    private Product product;

    public Stock(int amount) {
        this.amount = amount;
    }

    /**
     * 재고 수량을 감소시킨다.
     *
     * @param quantity 감소할 수량
     * */
    protected void decreaseStock(int quantity) {
        this.amount -= quantity;
    }

    /**
     * 재고 수량을 롤백한다.
     *
     * @param quantity 롤백할 수량
     * */
    protected void rollbackStock(int quantity) {
        this.amount += quantity;
    }
}
