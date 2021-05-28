package com.awse.commerce.domains.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
@Getter
public class CartItemDetailsDto {
    // 상품번호, 상품이름, 상품이미지, 주문개수, 상품가격
    private Long itemId;
    private String itemName;
    private String imgPath;
    private int orderCount;
    private int itemAmount;

    public int calTotalAmount() {
        return this.itemAmount * this.orderCount;
    }
}
