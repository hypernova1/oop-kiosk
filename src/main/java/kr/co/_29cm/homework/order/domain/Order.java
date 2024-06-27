package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.common.repository.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    @PrimaryKey
    private String orderNo;

    private final List<OrderItem> items = new ArrayList<>();

    private static final int FREE_SHIPPING_THRESHOLD = 50_000;

    protected Order() {}

    public static Order from(Cart cart) {
        Order order = new Order();
        order.orderNo = generateOrderNo();
        order.setOrderItems(cart.getProducts());
        return order;
    }

    /**
     * 장바구니 상품 목록을 기반으로 주문 아이템 목록을 생성한다.
     *
     * @param cartProducts 장바구니 상품 목록
     * */
    private void setOrderItems(List<CartProduct> cartProducts) {
        for (CartProduct cartProduct : cartProducts) {
            OrderItem orderItem = OrderItem.from(cartProduct);
            this.items.add(orderItem);
        }
    }

    /**
     * 주문 번호를 가져온다.
     * */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 주문 아이템 목록을 가져온다.
     * */
    public List<OrderItem> getOrderItems() {
        return this.items;
    }

    /**
     * 무료 배송 조건이 맞는지 확인한다.
     * */
    public boolean isFreeShipping() {
        return getTotalProductPrice() >= FREE_SHIPPING_THRESHOLD;
    }

    /**
     * 총 상품 금액을 가져온다.
     * */
    public int getTotalProductPrice() {
        return this.items.stream().mapToInt(OrderItem::getTotalProductPrice).sum();
    }

    /**
     * 주문 번호를 생성한다.
     * */
    static String generateOrderNo() {
        return DateTimeFormatter.ofPattern("YYYYMMddHHmmss").format(LocalDateTime.now());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.orderNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(this.orderNo, order.getOrderNo());
    }
}
