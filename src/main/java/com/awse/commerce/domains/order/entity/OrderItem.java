package com.awse.commerce.domains.order.entity;

import com.awse.commerce.domains.util.entity.BaseEntity;

public class OrderItem extends BaseEntity {

    private Long id;
    private int orderCount; // 개수
    private int orderItemAmount; // 가격

    private String item; // item엔티티와 연결

}
