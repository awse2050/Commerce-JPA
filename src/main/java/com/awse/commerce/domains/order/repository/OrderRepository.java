package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 전체 주문 조회
    @EntityGraph(attributePaths = {"orderer", "deliveryInfo", "orderItemList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select o from Order o")
    List<Order> getOrders();

    // 이름으로 주문조회
    // myOrderService 에 사용
    @EntityGraph(attributePaths = {"orderer","deliveryInfo","orderItemList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select o from Order o where o.orderer.name = :name")
    List<Order> findAllByName(@Param("name")String name);



}
