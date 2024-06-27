package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;

public class OrderItem {

    private String productNo;
    private int productPrice;
    private int quantity;

    /**
     * 장바구니 상품 정보로 주문 아이템을 생성한다.
     *
     * @param cartProduct 장바구니 상품 정보
     * @return 주문 아이템
     * */
    public static OrderItem from(CartProduct cartProduct) {
        OrderItem orderItem = new OrderItem();
        orderItem.productNo = cartProduct.getProduct().productNo();
        orderItem.quantity = cartProduct.getQuantity();
        orderItem.productPrice = cartProduct.getProduct().price();
        return orderItem;
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
     * 주문 수량을 가져온다.
     *
     * @return 주문 수량
     * */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * 상품 가격을 가져온다.
     *
     * @return 상품 가격
     * */
    public int getProductPrice() {
        return this.productPrice;
    }

    /**
     * 총 상품 가격을 가져온다.
     *
     * @return 총 상품 가격
     * */
    public int getTotalProductPrice() {
        return this.productPrice * this.quantity;
    }
}
