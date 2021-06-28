package com.awse.commerce.domains.order.dto;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.order.entity.Order;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class MyOrderViewDetailsDto {

    // 주문된 상품의 정보
    // 상품번호, 이름, 가격 ,이미지 , 주문된 수량, 주문상태(배송정보)
    // 없지만 추가할 부분 - 쿠폰할인, 적립금
    private Long itemId;
    private String itemName;
    private int itemTotalAmount;
    private String itemImg;
    private int orderCount;
    private String orderStatus;

//    @Builder
//    public MyOrderViewDetailsDto(Long itemId, String itemName, int itemTotalAmount, String itemImg,
//                                 int orderCount, String orderStatus) {
//        this.itemId = itemId;
//        this.itemName = itemName;
//        this.itemTotalAmount = itemTotalAmount;
//        this.itemImg = itemImg;
//        this.orderCount = orderCount;
//        this.orderStatus = orderStatus;
//    }

    public static List<MyOrderViewDetailsDto> from(Order entity) {
        return entity.getOrderItemList().stream().map(orderItem -> {

            Item item = orderItem.getItem();
            return MyOrderViewDetailsDto.builder()
                    .itemId(item.getItemId())
                    .itemName(item.getName())
                    .itemTotalAmount(orderItem.getOrderItemAmount())
                    .itemImg(item.getImgPath())
                    .orderCount(orderItem.getOrderCount())
                    .orderStatus(entity.getOrderStatus().name())
                    .build();

        }).collect(Collectors.toList());
    }

}
