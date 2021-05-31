package com.awse.commerce.domains.order.dao;

import com.awse.commerce.domains.order.dto.OrderRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderRequestDao {

    private List<OrderRequestDto> orderRequestDtoList;
    // BootPay API
    private String receiptId;
    private int price;

    public OrderRequestDao(List<OrderRequestDto> orderRequestDtoList) {
        this.orderRequestDtoList = orderRequestDtoList;
    }

    public OrderRequestDao(List<OrderRequestDto> orderRequestDtoList, String receiptId, int price) {
        this.orderRequestDtoList = orderRequestDtoList;
        this.receiptId = receiptId;
        this.price = price;
    }

}
