package com.awse.commerce.domains.item.entity;

import com.awse.commerce.domains.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Entity
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String imgPath; // 이미지

    @Column(nullable = false)
    private String name; // 상품명

    @Column(nullable = false)
    private int money; // 가격

    @Column(nullable = false)
    private int stockQuantity; // 재고 수량

}
