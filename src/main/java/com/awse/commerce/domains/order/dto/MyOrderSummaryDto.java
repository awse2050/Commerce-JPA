package com.awse.commerce.domains.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MyOrderSummaryDto {
    private List<MyOrderDto> myOrderDtoList;
    private int total;

    public MyOrderSummaryDto(List<MyOrderDto> myOrderDtoList, int total) {
        this.myOrderDtoList = myOrderDtoList;
        this.total = total;
    }
}
