package kr.co._29cm.homework.view;

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

}
