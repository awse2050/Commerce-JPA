package com.awse.commerce.domains.cart.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class CartObject {

    private Long cartId;
    private Long itemId;
    private Integer orderCount;

    public CartObject(Long cartId, Long itemId, Integer orderCount) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.orderCount = orderCount;
    }

}
