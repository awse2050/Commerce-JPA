package com.awse.commerce.domains.cart.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartListDto {
    private List<CartItemDetailsDto> cartItemDetailsDtoList;
    private int cartTotal;
    private int totalAmount;

    public CartListDto(List<CartItemDetailsDto> dtoList, int cartTotal) {
        this.cartItemDetailsDtoList = dtoList;
        this.cartTotal = cartTotal;
        this.totalAmount = calTotalAmount();
    }

    // 총합 계산
    private int calTotalAmount() {
       int totalAmount = this.cartItemDetailsDtoList.stream().mapToInt(itemDetails -> itemDetails.calTotalCount()).sum();
       return totalAmount;
    }
}
