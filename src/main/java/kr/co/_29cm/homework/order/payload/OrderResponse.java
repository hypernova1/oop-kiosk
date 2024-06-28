package kr.co._29cm.homework.order.payload;

import kr.co._29cm.homework.payment.payload.PaymentDto;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public record OrderResponse(int productPrice, int shippingPrice, int totalPaidPrice, List<OrderResponseItem> products) {


    public OrderResponse(PaymentDto paymentDto, List<OrderRequestItem> productInfoList, List<ProductDto> productDtoList) {
        this(
                paymentDto.productPrice(),
                paymentDto.shippingPrice(),
                paymentDto.totalPaidPrice(),
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
