package kr.co._29cm.homework.payment.infra;

import kr.co._29cm.homework.payment.domain.Payment;
import kr.co._29cm.homework.payment.domain.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpsRepository extends JpaRepository<Payment, String>, PaymentRepository {
}
