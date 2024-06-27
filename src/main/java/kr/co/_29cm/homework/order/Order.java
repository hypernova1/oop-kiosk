package kr.co._29cm.homework.order;

import kr.co._29cm.homework.payment.Payment;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<OrderProduct> products = new ArrayList<>();

    private Payment payment;

}
