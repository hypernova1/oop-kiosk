package kr.co._29cm.homework.view;

import kr.co._29cm.homework.product.domain.Product;

import java.util.List;

public class OutputView {

    private OutputView() {}

    public static void printProducts(List<Product> products) {
        System.out.printf("%-10s %-60s %10s %10s%n", "상품번호", "상품명", "판매가격", "재고수량");
        for (Product product : products) {
            System.out.printf("%-10s %-60s %10d %10s",
                    product.getProductNo(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            System.out.println();
        }
    }

}
