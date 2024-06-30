package kr.co._29cm.homework.ui.output;

import kr.co._29cm.homework.order.payload.OrderResponse;
import kr.co._29cm.homework.order.payload.OrderResponseItem;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public interface Output {

    void printProducts(List<ProductDto> productDtoList);
    void printOrderedProducts(List<OrderResponseItem> cartProducts);
    void printOrderDetail(OrderResponse orderResponse);
    void printQuitMessage();
    void printException(RuntimeException exception);
    void printBorderLine();

}
