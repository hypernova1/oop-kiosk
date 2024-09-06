package org.sam.kiosk.payment.adapter.out;

import org.sam.kiosk.payment.domain.Payment;
import org.sam.kiosk.payment.domain.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpsRepository extends JpaRepository<Payment, String>, PaymentRepository {
}
