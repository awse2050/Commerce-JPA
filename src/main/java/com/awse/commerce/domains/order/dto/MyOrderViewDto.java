package com.awse.commerce.domains.order.dto;

import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.util.embedded.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyOrderViewDto {

    // 주문번호, 주문금액, 주문날짜
    private Long orderId;
    private int totalAmount;
    private LocalDateTime orderedDate;

    // 주문자이름, 배송지정보, 연락처
    private String orderer;
    private String phone;
    private String deliveryAddress;

    private List<MyOrderViewDetailsDto> viewDtoList;

    public MyOrderViewDto(Order order) {
        this.orderId = order.getOrderId();
        this.totalAmount = order.getTotalAmount();
        this.orderedDate = order.getRegDate();
        this.orderer = order.getOrderer().getName();
        this.phone = order.getOrderer().getPhone();
        this.deliveryAddress = order.getDeliveryInfo().getDeliveryAddress();
        this.viewDtoList = MyOrderViewDetailsDto.from(order);
    }

}
