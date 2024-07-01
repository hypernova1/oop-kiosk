package kr.co._29cm.homework.order.payload;

import kr.co._29cm.homework.payment.payload.PaymentResponse;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.payload.ProductDto;

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
