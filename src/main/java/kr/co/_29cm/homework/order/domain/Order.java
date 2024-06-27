package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.payment.Payment;
import kr.co._29cm.homework.product.domain.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    @PrimaryKey
    private String orderNo;

    private final List<OrderProduct> products = new ArrayList<>();

    private Payment payment;

    protected Order() {}

    public static Order from(List<CartProduct> cartProducts) {
        Order order = new Order();
        order.orderNo = generateOrderNo();
        for (CartProduct cartProduct : cartProducts) {
            OrderProduct orderProduct = OrderProduct.from(cartProduct);
            order.products.add(orderProduct);
        }
        return order;
    }

    public String getOrderNo() {
        return this.orderNo;
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
