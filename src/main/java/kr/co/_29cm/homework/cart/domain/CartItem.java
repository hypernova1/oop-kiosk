package kr.co._29cm.homework.cart.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import kr.co._29cm.homework.common.BaseUuidEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseUuidEntity {

    @Column
    private String productNo;

    @Column
    private int quantity;

    @ManyToOne
    private Cart cart;

    /**
     * 장바구니 상품을 생성한다.
     *
     * @param product 상품 정보
     * @param quantity 수량
     * */
    public static CartItem of(String productNo, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.productNo = productNo;
        cartItem.quantity = quantity;
        return cartItem;
    }

    /**
     * 수량을 추가한다.
     *
     * @param quantity 수량
     * */
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
