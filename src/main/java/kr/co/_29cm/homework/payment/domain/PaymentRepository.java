package kr.co._29cm.homework.payment.domain;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByOrderNo(String orderNo);
}
