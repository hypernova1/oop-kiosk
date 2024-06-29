package kr.co._29cm.homework.payment.application;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.payment.domain.Payment;
import kr.co._29cm.homework.payment.domain.PaymentNotFoundException;
import kr.co._29cm.homework.payment.domain.PaymentRepository;
import kr.co._29cm.homework.payment.payload.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    /**
     * 주문 정보를 기반으로 결제 정보를 생성한다.
     * 
     * @param order 주문 정보
     * */
    @Transactional
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
    public PaymentResponse findOne(String orderNo) {
        Payment payment = this.paymentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new PaymentNotFoundException(orderNo));
        return new PaymentResponse(payment);
    }
}
