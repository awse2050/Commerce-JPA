package com.awse.commerce.domains.cart.dao;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CheckoutDao {
    // 상품번호
    private Long itemId;
    // 주문개수
    private int orderCount;

}
