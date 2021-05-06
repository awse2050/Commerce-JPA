package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
