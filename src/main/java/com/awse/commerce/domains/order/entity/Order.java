package com.awse.commerce.domains.order.entity;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.order.exception.OrderBadRequestException;
import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import com.awse.commerce.domains.util.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@ToString(exclude = {"orderer", "deliveryInfo", "orderItemList"})
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

    // 배송상태는 주문들어갈떄 생성되므로 같이 상태변화전이가 필요하다?
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery deliveryInfo; // Delivery 엔티티로 연관관계 설정

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id") // 반대 테이블에서 외래키가 잡힌다.
    @Builder.Default // Builder 사용시 결과값 null 방지용으로 기본값을 선언
    private List<OrderItem> orderItemList = new ArrayList<>(); // OrderItem 엔티티로 연관관계 설정.

    // 주문 만들기
    public Order(Member orderer, Delivery deliveryInfo, List<OrderItem> orderItemList ) {
        this.orderer = orderer;
        this.deliveryInfo = deliveryInfo;
        this.orderItemList = orderItemList;
        this.orderStatus = OrderStatus.ORDERED;
        //주문 총액 계산
        this.calTotalAmount();
   }

    // 사용필요성 아직 x
    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    // 주문 취소하기
    public void cancel() {
        // 주문상태 또는 배송상태가 ORDERED가 아니거나 READY가 아니면 에러발생
        if(this.deliveryInfo.getDeliveryStatus() != DeliveryStatus.READY) {
            throw new OrderBadRequestException("배송중이거나 완료된 주문은 취소할 수 없습니다.");
        }
        // 재고계산
        this.orderItemList.stream().forEach(orderItem -> orderItem.cancel());
        // 주문 상태변경
        this.orderStatus = OrderStatus.CANCEL;
    }
    // 주문 총액 계산
    private void calTotalAmount() {
        this.totalAmount = this.orderItemList.stream()
                .mapToInt(orderItem -> orderItem.getOrderItemAmount()).sum();
    }
}
