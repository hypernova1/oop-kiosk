package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public class OutputView {

    private OutputView() {}

    public static void printProducts(List<ProductDto> productDtoList) {
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

    public static void printCartProducts(List<CartProduct> cartProducts) {
        System.out.println("주문 내역:");
        printBorderLine();
        for (CartProduct cartProduct : cartProducts) {
            System.out.println(cartProduct.getProduct().name() + " - " + cartProduct.getQuantity() + "개");
        }
        printBorderLine();
    }

    public static void thanksToCustomer() {
        System.out.println("고객님 주문 감사합니다.");
    }

    public static void printBadInput() {
        System.out.println("잘못된 주문 입력입니다.");
    }

    public static void printException(String message) {
        System.out.println(message);
    }

    public static void printBorderLine() {
        System.out.println("----------------------------------------");
    }
}
