package com.awse.commerce.domains.order.dto;

import com.awse.commerce.domains.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<MyOrderDto> from(Page<Order> list) {
        return list.stream()
                .map(o -> MyOrderDto.builder()
                        .orderId(o.getOrderId())
                        .orderStatus(o.getOrderStatus().name())
                        .representativeImgPath(o.getOrderItemList().get(0).getItem().getImgPath())
                        .representativeItemName(o.getOrderItemList().get(0).getItem().getName())
                        .totalAmount(o.getTotalAmount())
                        .build()
                ).collect(Collectors.toList());
    }
}
