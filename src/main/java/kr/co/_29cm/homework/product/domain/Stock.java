package kr.co._29cm.homework.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import kr.co._29cm.homework.common.BaseUuidEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseUuidEntity {

    private int stock;
    @OneToOne
    private Product product;

    public Stock(int stock) {
        this.stock = stock;
    }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }
}
