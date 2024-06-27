package kr.co._29cm.homework.payment.application;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.payment.domain.Payment;
import kr.co._29cm.homework.payment.domain.PaymentNotFoundException;
import kr.co._29cm.homework.payment.domain.PaymentRepository;
import kr.co._29cm.homework.payment.payload.PaymentDto;
import kr.co._29cm.homework.product.application.ProductService;

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;

    public PaymentService(PaymentRepository paymentRepository, ProductService productService) {
        this.paymentRepository = paymentRepository;
        this.productService = productService;
    }

    /**
     * 주문 정보를 기반으로 결제 정보를 생성한다.
     * 
     * @param 주문 정보
     * */
    public void create(Order order) {
        Payment payment = Payment.from(order);
        this.paymentRepository.save(payment);
    }

    /**
     * 주문번호로 결제 정보를 찾는다.
     *
     * @param 주문 번호
     * @return 결제 정보
     * */
    public PaymentDto findOne(String orderNo) {
        Payment payment = this.paymentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new PaymentNotFoundException(orderNo));
        return new PaymentDto(payment);
    }
}
