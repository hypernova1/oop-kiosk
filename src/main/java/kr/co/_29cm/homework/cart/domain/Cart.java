package kr.co._29cm.homework.cart.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.common.BaseUuidEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Comment("장바구니")
@Table(name = "cart", indexes = @Index(name = "idx_cart_user_id", columnList = "user_id"))
public class Cart extends BaseUuidEntity {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CartItem> items = new ArrayList<>();

    @Comment("유저 아이디")
    @Column(name = "user_id", nullable = false, columnDefinition = "varchar(255)")
    private String userId;

    private Cart(String userId) {
        this.userId = userId;
    }

    /**
     * 장바구니를 생성한다.
     *
     * @param userId 유저 아이디
     * @return 장바구니
     * */
    public static Cart create(String userId) {
        return new Cart(userId);
    }

    /**
     * 장바구니 상품을 추가한다. 이미 존재하는 상품이라면 수량을 추가한다.
     *
     * @param productNo 상품 번호
     * @param quantity 주문 수량
     * */
    public void addProduct(String productNo, int quantity) {
        Optional<CartItem> optionalCartProduct = findCartItem(productNo);
        if (optionalCartProduct.isPresent()) {
            optionalCartProduct.get().addQuantity(quantity);
            return;
        }
        this.items.add(CartItem.of(this, productNo, quantity));
    }

    /**
     * 상품이 이미 장바구니에 담겨있는지 찾는다.
     *
     * @param productNo 상품 번호
     * @return 동일한 상품
     * */
    private Optional<CartItem> findCartItem(String productNo) {
        return this.items.stream()
                .filter(cartItem -> cartItem.getProductNo().equals(productNo))
                .findFirst();
    }

    /**
     * 장바구니를 비운다.
     * */
    public void clear() {
        this.items.clear();
    }

    /**
     * 장바구니가 비어있는지 확인한다.
     *
     * @return 장바구니 비어있음 여부
     * */
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}

