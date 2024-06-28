package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.order.payload.OrderRequestItem;

public class OrderItem {

    private String productNo;
    private int productPrice;
    private int quantity;

    /**
     * 장바구니 상품 정보로 주문 아이템을 생성한다.
     *
     * @param orderRequestItem 장바구니 상품 정보
     * @return 주문 아이템
     * */
    public static OrderItem of(OrderRequestItem orderRequestItem, int price) {
        OrderItem orderItem = new OrderItem();
        orderItem.productNo = orderRequestItem.productNo();
        orderItem.quantity = orderRequestItem.quantity();
        orderItem.productPrice = price;
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
