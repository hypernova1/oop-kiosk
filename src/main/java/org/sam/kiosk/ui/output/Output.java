package org.sam.kiosk.ui.output;

import org.sam.kiosk.order.application.payload.OrderResponse;
import org.sam.kiosk.order.application.payload.OrderResponseItem;
import org.sam.kiosk.product.application.payload.ProductDto;

import java.util.List;

public interface Output {

    void printProducts(List<ProductDto> productDtoList);
    void printOrderedProducts(List<OrderResponseItem> cartProducts);
    void printOrderDetail(OrderResponse orderResponse);
    void printQuitMessage();
    void printException(RuntimeException exception);
    void printBorderLine();

}
