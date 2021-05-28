package com.awse.commerce.domains.cart.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutDao {
    // 상품번호
    private Long itemId;
    // 주문개수
    private int orderCount;

}
