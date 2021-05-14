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
@ToString(exclude = "item")
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

    // 주문상품 정보받기
    public OrderItem(Item itemEntity, int orderCount) {
        this.item = itemEntity;
        this.orderCount = orderCount;

        this.calOrderItemAmount(orderCount);
    }

    // 주문상품 총 금액 계산
    private void calOrderItemAmount(int orderCount) {
        this.orderItemAmount = this.item.getMoney() * orderCount;
    }

    // 주문될 떄 발생
    public void removeStockQuantity() {
        this.item.removeStockQuantity(orderCount);
    }

    // 주문취소시 발생 ( 취소 )
    public void cancel() {
        this.item.addStockQuantity(this.orderCount);
    }
}
