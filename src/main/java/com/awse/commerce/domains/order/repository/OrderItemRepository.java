package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
