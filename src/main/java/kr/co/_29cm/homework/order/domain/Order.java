package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.payment.Payment;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<OrderProduct> products = new ArrayList<>();

    private Payment payment;

    public static void from(List<CartProduct> cartProducts) {

    }
}
