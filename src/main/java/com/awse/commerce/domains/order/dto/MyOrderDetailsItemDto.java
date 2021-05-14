package com.awse.commerce.domains.order.dto;

import lombok.*;

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
}
