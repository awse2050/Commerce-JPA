package com.awse.commerce.domains.cart.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRequestItemDao {
    // 상품번호, 주문수량
    private Long itemId;
    // 최소 1이상
    private int orderCount;

}
