package com.awse.commerce.domains.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class OrderInfoDto {
    // 주문번호, 주문상태, 주문상품 개수, 배송정보, 주문자의 이름
    private Long orderId;

    private String orderStatus;
    private String deliveryStatus;
    private String ordererName;

    private LocalDateTime regdate;
    private int orderItemCount;

    @QueryProjection
    public OrderInfoDto(Long orderId, String ordererName, String orderStatus, String deliveryStatus, int orderItemCount, LocalDateTime regdate) {
        this.orderId = orderId;
        this.ordererName = ordererName;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.orderItemCount = orderItemCount;
        this.regdate = regdate;
    }
}
