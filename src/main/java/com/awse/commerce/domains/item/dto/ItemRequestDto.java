package com.awse.commerce.domains.item.dto;

import com.awse.commerce.domains.item.entity.Item;
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

    public static Item getItemEntity(ItemRequestDto dto) {
        return Item.builder()
                .name(dto.getItemName())
                .imgPath(dto.getImgPath())
                .money(dto.getItemAmount())
                .stockQuantity(dto.getStockQuantity())
                .build();
    }
}