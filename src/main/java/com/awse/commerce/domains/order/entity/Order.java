package com.awse.commerce.domains.order.entity;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private int totalAmount;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    // 주문을 따로따로 한사람이 여러번 했을 가능성 - ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member orderer; // 주문자가 누군지 Member 엔티티로 연관관계 설정.

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery deliveryInfo; // Delivery 엔티티로 연관관계 설정

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // 반대 테이블에서 외래키가 잡힌다.
    private List<OrderItem> orderItemList; // OrderItem 엔티티로 연관관계 설정.
}

