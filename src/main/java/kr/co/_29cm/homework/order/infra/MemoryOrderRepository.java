package kr.co._29cm.homework.order.infra;

import kr.co._29cm.homework.common.repository.DefaultMemoryRepository;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryOrderRepository extends DefaultMemoryRepository<Order, String> implements OrderRepository {
}
