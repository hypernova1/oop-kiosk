package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.cart.domain.CartProduct;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.payload.ProductQuantityCheckDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public void create(List<CartProduct> cartProducts) {
        List<ProductQuantityCheckDto> productQuantityCheckDtos = cartProducts.stream().map((cartProduct) -> new ProductQuantityCheckDto(cartProduct.getProductNo(), cartProduct.getQuantity()))
                .collect(Collectors.toList());

        productService.checkQuantity(productQuantityCheckDtos);
    }

}
