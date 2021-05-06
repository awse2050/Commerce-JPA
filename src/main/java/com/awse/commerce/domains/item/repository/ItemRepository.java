package com.awse.commerce.domains.item.repository;

import com.awse.commerce.domains.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
