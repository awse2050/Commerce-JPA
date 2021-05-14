package com.awse.commerce.domains.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    // 상품이름, 가격, 재고수량, 이미지
    private String itemName;
    private String imgPath;
    private int itemAmount;
    private int stockQuantity;
}
