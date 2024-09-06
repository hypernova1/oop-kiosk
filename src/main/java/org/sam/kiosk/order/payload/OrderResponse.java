package org.sam.kiosk.order.payload;

import org.sam.kiosk.payment.payload.PaymentResponse;
import org.sam.kiosk.product.domain.ProductNotFoundException;
import org.sam.kiosk.product.payload.ProductDto;

import java.util.List;

public record OrderResponse(int productPrice, int shippingPrice, int totalPaidPrice, List<OrderResponseItem> products) {


    public OrderResponse(PaymentResponse paymentResponse, List<OrderRequestItem> orderRequestItems, List<ProductDto> productDtoList) {
        this(
                paymentResponse.productPrice(),
                paymentResponse.shippingPrice(),
                paymentResponse.totalPaidPrice(),
                productDtoList.stream().map((productDto -> {
                    OrderRequestItem orderRequestItem = orderRequestItems.stream()
                            .filter((productInfo) -> productInfo.productNo().equals(productDto.productNo()))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException(productDto.productNo()));

                    return new OrderResponseItem(productDto.name(), orderRequestItem.quantity());
                })).toList()
        );

    }
}
