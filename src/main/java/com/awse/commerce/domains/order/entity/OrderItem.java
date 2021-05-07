package com.awse.commerce.domains.order.entity;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Getter
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    private int orderCount; // 주문수량

    @Column(nullable = false)
    private int orderItemAmount; // 주문총액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // item엔티티와 연결

}
