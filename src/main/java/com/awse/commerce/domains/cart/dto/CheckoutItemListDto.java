package com.awse.commerce.domains.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 체크아웃에서 보여줄 아이템목록을 받는다.
public class CheckoutItemListDto {
    private List<CartItemDetailsDto> checkoutList;
    private int totalAmount;

    public CheckoutItemListDto(List<CartItemDetailsDto> checkoutList) {
        this.checkoutList = checkoutList;
        this.totalAmount = calTotalAmount();
    }

    private int calTotalAmount() {
        return this.checkoutList.stream().mapToInt(item -> item.calTotalAmount()).sum();
    }
}
