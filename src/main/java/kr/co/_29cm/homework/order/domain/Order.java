package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.payment.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    @PrimaryKey
    private String orderNo;

    private final List<OrderItem> items = new ArrayList<>();

    private Payment payment;

    protected Order() {}

    public static Order from(List<CartProduct> cartProducts) {
        Order order = new Order();
        order.orderNo = generateOrderNo();
        order.setOrderItems(cartProducts, order);
        return order;
    }

    private void setOrderItems(List<CartProduct> cartProducts, Order order) {
        for (CartProduct cartProduct : cartProducts) {
            OrderItem orderItem = OrderItem.from(cartProduct);
            order.items.add(orderItem);
        }
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public List<OrderItem> getOrderItems() {
        return this.items;
    }

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
