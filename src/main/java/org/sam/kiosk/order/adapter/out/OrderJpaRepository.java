package org.sam.kiosk.order.adapter.out;

import org.sam.kiosk.order.domain.Order;
import org.sam.kiosk.order.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, String>, OrderRepository {
}
