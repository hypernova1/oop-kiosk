package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.payment.domain.Payment;
import kr.co._29cm.homework.payment.payload.PaymentDto;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public class OutputView {

    private OutputView() {}

    /**
     * 상품 목록을 출력한다.
     * */
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

    /**
     * 주문할 상품 목록을 출력한다.
     * */
    public static void printCartProducts(List<CartProduct> cartProducts) {
        System.out.println("주문 내역:");
        for (CartProduct cartProduct : cartProducts) {
            System.out.println(cartProduct.getProduct().name() + " - " + cartProduct.getQuantity() + "개");
        }
        printBorderLine();
    }

    /**
     * 결제 정보를 출력한다.
     * */
    public static void printPayment(PaymentDto paymentDto) {
        System.out.println("주문금액: " + paymentDto.productPrice());
        if (paymentDto.shippingPrice() > 0) {
            System.out.println("배송비: " + paymentDto.shippingPrice());
        }
        printBorderLine();
        System.out.println("지불금액: " + paymentDto.totalPaidPrice());
        printBorderLine();
    }

    /**
     * 감사 인사를 출력한다.
     * */
    public static void thanksToCustomer() {
        System.out.println("고객님 주문 감사합니다.");
    }

    /**
     * 잘못된 주문임을 알리는 메시지를 출력한다.
     * */
    public static void printBadInput() {
        System.out.println("잘못된 주문 입력입니다.");
    }

    /**
     * 예외 발생 사항을 출력한다.
     * */
    public static void printException(RuntimeException exception) {
        System.out.println(exception.getMessage());
    }

    /**
     * 구분선을 출력한다.
     * */
    public static void printBorderLine() {
        System.out.println("----------------------------------------");
    }
}
