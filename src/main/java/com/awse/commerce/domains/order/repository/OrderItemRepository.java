package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.order.entity.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @EntityGraph(attributePaths = "item", type = EntityGraph.EntityGraphType.LOAD)
    List<OrderItem> findAll();

    @Query("select ol from OrderItem ol join fetch ol.item i")
    List<OrderItem> findAllJoinFetch();

}
