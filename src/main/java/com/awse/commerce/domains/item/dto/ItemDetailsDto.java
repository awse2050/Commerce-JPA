package com.awse.commerce.domains.item.dto;

import com.awse.commerce.domains.item.entity.Item;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 상품 조회시 사용
public class ItemDetailsDto {
    // 상품번호, 이름, 가격, 이미지, 재고수량
    private Long itemId;

    private String itemName;
    private int itemAmount;
    private String imgPath;

    private int stockQuantity;

    public ItemDetailsDto(Item entity) {
        this.itemId = entity.getItemId();
        this.itemName = entity.getName();
        this.itemAmount = entity.getMoney();
        this.imgPath = entity.getImgPath();
        this.stockQuantity = entity.getStockQuantity();
    }
}
