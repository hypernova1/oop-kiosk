package kr.co._29cm.homework;


import kr.co._29cm.homework.cart.application.CartService;
import kr.co._29cm.homework.cart.infra.MemoryCartProductRepository;
import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.order.infra.MemoryOrderRepository;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.infra.MemoryProductRepository;
import kr.co._29cm.homework.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ProductService productService = new ProductService(new MemoryProductRepository());
        CartService cartService = new CartService(new MemoryCartProductRepository());
        OrderService orderService = new OrderService(new MemoryOrderRepository(), productService);

        OutputView.printProducts(productService.getAll());

//        boolean exit = false;
//        while (!exit) {
//
//
//        }

    }
}