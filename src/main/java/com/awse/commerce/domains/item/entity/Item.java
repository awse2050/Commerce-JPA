package com.awse.commerce.domains.item.entity;

import com.awse.commerce.domains.util.entity.BaseEntity;

public class Item extends BaseEntity {

    private Long itemId;
    private String imgPath; // 이미지
    private String name; // 상품명
    private int money; // 가격
    private int stockQuantity; // 재고 수량

}
