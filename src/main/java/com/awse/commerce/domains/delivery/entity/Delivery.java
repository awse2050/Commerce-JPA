package com.awse.commerce.domains.delivery.entity;

import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.DeliveryStatus;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Delivery extends BaseEntity {

    private Long deliveryId;
    @Embedded
    private Address address; // 임베디드 타입을 사용해서 변경

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송상태를 enum으로 연결결

}
