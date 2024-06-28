package kr.co._29cm.homework.order.payload;

import kr.co._29cm.homework.payment.payload.PaymentResponse;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public record OrderResponse(int productPrice, int shippingPrice, int totalPaidPrice, List<OrderResponseItem> products) {


    public OrderResponse(PaymentResponse paymentResponse, List<OrderRequestItem> productInfoList, List<ProductDto> productDtoList) {
        this(
                paymentResponse.productPrice(),
                paymentResponse.shippingPrice(),
                paymentResponse.totalPaidPrice(),
                productDtoList.stream().map((productDto -> {
                    OrderRequestItem orderRequestItem = productInfoList.stream()
                            .filter((productInfo) -> productInfo.productNo().equals(productDto.productNo()))
                            .findFirst()
                            .orElse(null);

                    assert orderRequestItem != null;

                    return new OrderResponseItem(productDto.name(), orderRequestItem.quantity());
                })).toList()
        );

    }
}
