package com.awse.commerce.domains.cart.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyRequestItemDao {
    // 상품번호, 주문수량
    private Long itemId;
    // 최소 1이상
    @Min(value = 1, message = "최소 1개 이상 입력해주세요.")
    private int orderCount;
}
