package org.sam.kiosk.ui.output;

import org.sam.kiosk.order.payload.OrderResponse;
import org.sam.kiosk.order.payload.OrderResponseItem;
import org.sam.kiosk.product.payload.ProductDto;
import org.sam.kiosk.util.NumberUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputView implements Output {

    /**
     * 상품 목록을 출력한다.
     * */
    public void printProducts(List<ProductDto> productDtoList) {
        System.out.printf("%-10s %-60s %10s %10s%n", "상품번호", "상품명", "판매가격", "재고수량");
        for (ProductDto product : productDtoList) {
            System.out.printf("%-10s %-60s %10d %10s",
                    product.productNo(),
                    product.name(),
                    product.price(),
                    product.quantity());
            System.out.println();
        }
    }

    /**
     * 주문할 상품 목록을 출력한다.
     * */
    public void printOrderedProducts(List<OrderResponseItem> cartProducts) {
        System.out.println("주문 내역:");
        printBorderLine();
        for (OrderResponseItem orderResponseItem : cartProducts) {
            System.out.println(orderResponseItem.name() + " - " + orderResponseItem.quantity() + "개");
        }
        printBorderLine();
    }

    /**
     * 주문 정보를 출력한다.
     * */
    public void printOrderDetail(OrderResponse orderResponse) {
        printOrderedProducts(orderResponse.products());
        System.out.println("주문금액: " + NumberUtil.toMoneyFormat(orderResponse.productPrice()));
        if (orderResponse.shippingPrice() > 0) {
            System.out.println("배송비: " + NumberUtil.toMoneyFormat(orderResponse.shippingPrice()));
        }
        printBorderLine();
        System.out.println("지불금액: " + NumberUtil.toMoneyFormat(orderResponse.totalPaidPrice()));
        printBorderLine();
    }

    /**
     * 감사 인사를 출력한다.
     * */
    public void printQuitMessage() {
        System.out.println("고객님 주문 감사합니다.");
    }

    /**
     * 잘못된 주문임을 알리는 메시지를 출력한다.
     * */
    public void printBadInput() {
        System.out.println("잘못된 주문 입력입니다.");
    }

    /**
     * 예외 발생 사항을 출력한다.
     * */
    public void printException(RuntimeException exception) {
        System.out.println(exception.getMessage());
    }

    /**
     * 구분선을 출력한다.
     * */
    public void printBorderLine() {
        System.out.println("----------------------------------------");
    }
}
