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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(OrderingMachine orderingMachine) {
        return args -> {
            orderingMachine.process();
        };
    }
}