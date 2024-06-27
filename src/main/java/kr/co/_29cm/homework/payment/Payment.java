package kr.co._29cm.homework.payment;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderItem;
import kr.co._29cm.homework.product.payload.ProductPriceInfo;

import java.util.List;

public class Payment {

    private int productPrice;
    private int shippingPrice;
    private int totalPrice;
    private String orderNo;

    public static Payment from(Order order, List<ProductPriceInfo> productPriceInfos) {
        Payment payment = new Payment();
        payment.orderNo = order.getOrderNo();
        payment.productPrice = payment.calculateProductPrice(order.getOrderItems(), productPriceInfos);
        return payment;
    }

    private int calculateProductPrice(List<OrderItem> orderItems, List<ProductPriceInfo> productPriceInfos) {
        int totalProductPrice = 0;
        for (OrderItem orderItem : orderItems) {
            ProductPriceInfo productPriceInfo = productPriceInfos.stream()
                    .filter((priceInfo -> orderItem.getProductNo().equals(priceInfo.productNo())))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

            totalProductPrice += productPriceInfo.price() * orderItem.getQuantity();
        }
        return totalProductPrice;
    }
}
