package kr.co._29cm.homework;


import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.order.infra.MemoryOrderRepository;
import kr.co._29cm.homework.payment.MemoryPaymentRepository;
import kr.co._29cm.homework.payment.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.infra.MemoryProductRepository;
import kr.co._29cm.homework.view.OrderingMachine;

public class Application {
    public static void main(String[] args) {

        ProductService productService = new ProductService(new MemoryProductRepository());
        PaymentService paymentService = new PaymentService(new MemoryPaymentRepository(), productService);
        OrderService orderService = new OrderService(new MemoryOrderRepository(), productService, paymentService);


        OrderingMachine orderingMachine = new OrderingMachine(productService);
        orderingMachine.run();


    }
}