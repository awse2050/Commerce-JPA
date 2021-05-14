package com.awse.commerce.domains.cart.repository;

import com.awse.commerce.domains.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
