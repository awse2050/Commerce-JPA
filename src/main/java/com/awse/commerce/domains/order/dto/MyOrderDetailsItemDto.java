package com.awse.commerce.domains.order.dto;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.order.entity.OrderItem;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyOrderDetailsItemDto {
    // 상품번호, 이름, 가격, 이미지
    private Long itemId;

    private String itemName;

    private int itemAmount;

    private String imgPath;

    public static List<MyOrderDetailsItemDto> from(List<OrderItem> list) {
        return list.stream()
                .map(itemList -> {
                    Item item = itemList.getItem();
                    return new MyOrderDetailsItemDto(item.getItemId(), item.getName(), item.getMoney(), item.getImgPath());
                }).collect(Collectors.toList());
    }
}
