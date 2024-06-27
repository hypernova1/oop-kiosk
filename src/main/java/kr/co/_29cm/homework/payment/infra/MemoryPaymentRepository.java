package kr.co._29cm.homework.payment.infra;

import kr.co._29cm.homework.common.repository.DefaultMemoryRepository;
import kr.co._29cm.homework.payment.domain.Payment;
import kr.co._29cm.homework.payment.domain.PaymentRepository;

import java.util.Optional;

public class MemoryPaymentRepository extends DefaultMemoryRepository<Payment, String> implements PaymentRepository {
    @Override
    public Optional<Payment> findByOrderNo(String orderNo) {
        return this.items.stream().filter((item) -> item.getOrderNo().equals(orderNo)).findFirst();
    }
}
