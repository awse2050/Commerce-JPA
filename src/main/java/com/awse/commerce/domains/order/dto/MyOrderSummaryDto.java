package com.awse.commerce.domains.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MyOrderSummaryDto {
    private List<MyOrderInfoDto> myOrderInfoDtoList;
    private int total;

    public MyOrderSummaryDto(List<MyOrderInfoDto> myOrderInfoDtoList, int total) {
        this.myOrderInfoDtoList = myOrderInfoDtoList;
        this.total = total;
    }
}
