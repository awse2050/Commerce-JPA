package com.awse.commerce.domains.order.entity;

import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.OrderStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class Order extends BaseEntity {

    private Long orderId;
    private int totalAmount;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus; // enum으로 변경

    private String member; // 사용자가 누군지 Member 엔티티로 연관관계 설정.
    private String deliveryInfo; // Delivery 엔티티로 연관관계 설정

    private List orderItemList; // OrderItem 엔티티로 연관관계 설정.
}

