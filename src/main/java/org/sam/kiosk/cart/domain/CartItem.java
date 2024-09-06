package org.sam.kiosk.cart.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.sam.kiosk.common.BaseUuidEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_item")
@Comment("장바구니 상품")
public class CartItem extends BaseUuidEntity {

    @Comment("상품 번호")
    @Column(name = "product_no", nullable = false, columnDefinition = "varchar(255)")
    private String productNo;

    @Comment("수량")
    @Column(name = "quantity", nullable = false, columnDefinition = "int")
    private int quantity;

    @ManyToOne
    private Cart cart;

    /**
     * 장바구니 상품을 생성한다.
     *
     * @param cart 장바구니
     * @param productNo 상품 정보
     * @param quantity 수량
     * */
    public static CartItem of(Cart cart, String productNo, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.productNo = productNo;
        cartItem.quantity = quantity;
        cartItem.cart = cart;
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
