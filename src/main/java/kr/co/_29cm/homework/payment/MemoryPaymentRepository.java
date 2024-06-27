package kr.co._29cm.homework.payment;

import kr.co._29cm.homework.common.repository.DefaultMemoryRepository;

public class MemoryPaymentRepository extends DefaultMemoryRepository<Payment, String> implements PaymentRepository {
}
