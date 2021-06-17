package com.awse.commerce.domains.order.dto;

import com.awse.commerce.domains.order.entity.Order;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MyOrderDto {
    // 주문번호 주문날짜, 대표이미지, 대표이미지이름, 총액, 주문상태, 대표 상품번호
    private Long orderId;
    private Long representativeItemId;
    private String representativeImgPath;
    private String representativeItemName;
    private int totalAmount;
    private int orderCount;
    private LocalDateTime orderedDate;
    private String orderStatus;

    public static List<MyOrderDto> from(Page<Order> list) {
        return list.stream()
                .map(o -> MyOrderDto.builder()
                        .orderId(o.getOrderId())
                        .orderStatus(o.getOrderStatus().name())
                        .representativeItemId(o.getOrderItemList().get(0).getItem().getItemId())
                        .representativeImgPath(o.getOrderItemList().get(0).getItem().getImgPath())
                        .representativeItemName(o.getOrderItemList().get(0).getItem().getName())
                        .orderCount(o.getOrderItemList().size())
                        .totalAmount(o.getTotalAmount())
                        .orderedDate(o.getRegDate())
                        .build()
                ).collect(Collectors.toList());
    }
}
