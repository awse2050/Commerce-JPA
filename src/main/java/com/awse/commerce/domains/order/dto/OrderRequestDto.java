package com.awse.commerce.domains.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderRequestDto {

    private Long itemId;
    private int orderCount;

    @Builder
    public OrderRequestDto(Long itemId, int orderCount) {
        this.itemId = itemId;
        this.orderCount = orderCount;
    }

}
