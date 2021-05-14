package com.awse.commerce.domains.order.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 나의 주문의 정보를 전달할때 주문상품을 만드는 DTO
public class MyOrderDetailsDto {
    // 주문번호, 주문상태, 주문날짜, 주문 상품의 목록들
    private Long orderId;

    private String orderStatus;

    private LocalDateTime regdate;

    private List<MyOrderDetailsItemDto> orderedItemList;


}
