package com.awse.commerce.domains.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class MyOrderInfoDto {
    // 나의 주문에 대한 주문정보
    private Long orderId; // 주문번호

    private int totalAmount; // 총액

    private String orderStatus; // 주문상태
    private String deliveryStatus; // 배송상태

    private LocalDateTime regdate; // 등록날짜
    @Builder.Default
    private List<OrderItemDetailsDto> orderItemList = new ArrayList<>(); // 해당 주문정보에 쓰여진 주문상품들.


}
