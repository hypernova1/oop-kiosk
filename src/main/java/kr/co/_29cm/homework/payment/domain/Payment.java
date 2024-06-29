package kr.co._29cm.homework.payment.domain;

import kr.co._29cm.homework.common.repository.PrimaryKey;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderItem;
import kr.co._29cm.homework.product.payload.ProductPriceDto;

import java.util.List;
import java.util.UUID;

public class Payment {

    @PrimaryKey
    private String id;
    private int productPrice;
    private int shippingPrice;
    private int totalPaidPrice;
    private String orderNo;

    /**
     * 주문 데이터를 기반으로 결제 정보를 생성한다.
     *
     * @param order 주문 데이터
     * @return 결제 정보
     * */
    public static Payment from(Order order) {
        Payment payment = new Payment();
        payment.id = UUID.randomUUID().toString();
        payment.orderNo = order.getOrderNo();
        payment.productPrice = order.getTotalProductPrice();
        payment.shippingPrice = order.getShippingPrice();
        payment.totalPaidPrice = payment.productPrice + payment.shippingPrice;
        payment.orderNo = order.getOrderNo();
        return payment;
    }

    private int calculateProductPrice(List<OrderItem> orderItems, List<ProductPriceDto> productPriceDtos) {
        int totalProductPrice = 0;
        for (OrderItem orderItem : orderItems) {
            ProductPriceDto productPriceDto = productPriceDtos.stream()
                    .filter((priceInfo -> orderItem.getProductNo().equals(priceInfo.productNo())))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

            totalProductPrice += productPriceDto.price() * orderItem.getQuantity();
        }
        return totalProductPrice;
    }

    /**
     * 상품의 총 가격을 가져온다.
     *
     * @return 상품 총 금액
     * */
    public int getProductPrice() {
        return this.productPrice;
    }

    /**
     * 배송비를 가져온다.
     *
     * @return 배송비
     * */
    public int getShippingPrice() {
        return this.shippingPrice;
    }

    /**
     * 총 결제 금액을 가져온다.
     *
     * @return 총 결제 금액
     * */
    public int getTotalPaidPrice() {
        return this.totalPaidPrice;
    }

    public String getOrderNo() {
        return this.orderNo;
    }
}
