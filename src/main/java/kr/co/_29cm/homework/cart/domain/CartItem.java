package kr.co._29cm.homework.cart.domain;

import kr.co._29cm.homework.product.payload.ProductDto;

public class CartItem {

    private ProductDto product;
    private int quantity;

    /**
     * 장바구니 상품을 생성한다.
     *
     * @param product 상품 정보
     * @param quantity 수량
     * */
    public static CartItem of(ProductDto product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.product = product;
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

    /**
     * 상품 정보를 가져온다.
     *
     * @return 상품 정보
     * */
    public ProductDto getProduct() {
        return this.product;
    }

    /**
     * 수량을 가져온다.
     *
     * @return 수량
     * */
    public int getQuantity() {
        return this.quantity;
    }
}
