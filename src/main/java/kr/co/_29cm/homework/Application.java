package kr.co._29cm.homework;


import kr.co._29cm.homework.common.lock.MemoryLockManager;
import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.order.infra.MemoryOrderRepository;
import kr.co._29cm.homework.payment.infra.MemoryPaymentRepository;
import kr.co._29cm.homework.payment.application.PaymentService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.infra.MemoryProductRepository;
import kr.co._29cm.homework.view.OrderProcessHandler;
import kr.co._29cm.homework.view.OrderingMachine;

public class Application {
    public static void main(String[] args) {
        ProductService productService = new ProductService(new MemoryProductRepository(), new MemoryLockManager());
        PaymentService paymentService = new PaymentService(new MemoryPaymentRepository());
        OrderService orderService = new OrderService(new MemoryOrderRepository(), productService, paymentService);
        OrderProcessHandler orderProcessHandler = new OrderProcessHandler(productService, orderService, paymentService);

        OrderingMachine orderingMachine = new OrderingMachine(orderProcessHandler);
        orderingMachine.process();
    }
}