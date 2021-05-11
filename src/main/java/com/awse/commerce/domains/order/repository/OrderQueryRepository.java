package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.delivery.entity.QDelivery;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.entity.QMember;
import com.awse.commerce.domains.order.dto.OrderInfoDto;
import com.awse.commerce.domains.order.dto.QOrderInfoDto;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.QOrder;
import com.awse.commerce.domains.order.entity.QOrderItem;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class OrderQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory jpaQueryFactory;

    private QMember member;
    private QOrder order;
    private QDelivery delivery;
    private QOrderItem orderItem;

    public OrderQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.member = QMember.member;
        this.order = QOrder.order;
        this.delivery = QDelivery.delivery;
        this.orderItem = QOrderItem.orderItem;
    }

    // 전체 주문조회
    // 우선 불필요한 기능중 하나.
    public List<Order> findAll() {

        QueryResults<Order> queryResults = jpaQueryFactory.select(order)
                .from(order)
                .leftJoin(delivery).on(order.deliveryInfo.eq(delivery)).fetchJoin()
                .leftJoin(member).on(order.orderer.id.eq(member.id))
                .leftJoin(orderItem).on(order.orderItemList.isNotEmpty()).fetchJoin()
                .distinct()
                .fetchResults();

        return queryResults.getResults();
    }

    // 특정 사용자의 주문 조회
    public List<OrderInfoDto> findOrderByMember(Member findMember) {
        QueryResults<OrderInfoDto> queryResults = jpaQueryFactory
                .select(new QOrderInfoDto(
                        order.orderId,
                        member.name,
                        order.orderStatus.stringValue(),
                        delivery.deliveryStatus.stringValue(),
                        order.orderItemList.size(),
                        order.regDate))
                .from(order)
                .leftJoin(order.orderer, member)
                .leftJoin(order.deliveryInfo, delivery)
                .where(order.orderer.eq(findMember))
                .orderBy(order.orderId.desc())
                .fetchResults();

        List<OrderInfoDto> result = queryResults.getResults();

        return result;
    }
}
