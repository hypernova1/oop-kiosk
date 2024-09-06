package org.sam.kiosk.payment.application;

import org.sam.kiosk.payment.domain.Payment;
import org.sam.kiosk.payment.domain.PaymentNotFoundException;
import org.sam.kiosk.payment.domain.PaymentRepository;
import org.sam.kiosk.payment.application.payload.PaymentRequest;
import org.sam.kiosk.payment.application.payload.PaymentResponse;
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
     * @param request 주문 정보
     * */
    @Transactional
    public void create(PaymentRequest request) {
        Payment payment = Payment.from(request);
        this.paymentRepository.save(payment);
    }

    /**
     * 주문번호로 결제 정보를 찾는다.
     *
     * @param orderNo 주문 번호
     * @return 결제 정보
     * */
    public PaymentResponse findOne(String orderNo) {
        Payment payment = this.paymentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new PaymentNotFoundException(orderNo));
        return new PaymentResponse(payment);
    }
}
