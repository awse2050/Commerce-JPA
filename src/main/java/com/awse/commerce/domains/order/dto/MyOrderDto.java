package com.awse.commerce.domains.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyOrderDto {
    // 주문번호 주문날짜, 대표이미지, 대표이미지이름, 총액, 주문상태
    private Long orderId;
    private String representativeImgPath;
    private String representativeItemName;
    private int totalAmount;
    private String orderStatus;

}
