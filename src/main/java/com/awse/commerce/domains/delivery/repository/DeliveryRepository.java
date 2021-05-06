package com.awse.commerce.domains.delivery.repository;

import com.awse.commerce.domains.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
