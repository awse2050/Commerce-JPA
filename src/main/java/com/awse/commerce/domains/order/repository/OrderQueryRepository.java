package com.awse.commerce.domains.order.repository;

import com.awse.commerce.domains.delivery.entity.QDelivery;
import com.awse.commerce.domains.item.entity.QItem;
import com.awse.commerce.domains.member.entity.QMember;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.QOrder;
import com.awse.commerce.domains.order.entity.QOrderItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    private QOrder order;
    private QMember member;
    private QDelivery delivery;
    private QOrderItem orderItem;

    public OrderQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Order.class);
        this.queryFactory = jpaQueryFactory;
        this.order = QOrder.order;
        this.member = QMember.member;
        this.delivery = QDelivery.delivery;
        this.orderItem = QOrderItem.orderItem;
    }

    // 나의 전체 주문목록( 주문내역 조회 페이지 )
    public Page<Order> getMyOrders(Long memberId, Pageable pageable, String keyword) {

        QueryResults<Order> queryResults = queryFactory.select(order)
                .from(order)
                .leftJoin(order.orderer, member).fetchJoin()
                .leftJoin(order.deliveryInfo, delivery).fetchJoin()
                .leftJoin(order.orderItemList, orderItem).fetchJoin()
                .where(order.orderer.id.eq(memberId))
                .where(searchExpression(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.orderId.desc())
                .fetchResults();

        List<Order> orderList = queryResults.getResults();

        long total = queryResults.getTotal();

        return new PageImpl<>(orderList, pageable, total);
    }

    // 특정 주문의 정보(주문상품 등)
    public Optional<Order> getMyOrderDetails(Long orderId) {
        Order entity = queryFactory.select(order)
                .from(order)
                .leftJoin(order.orderer, member).fetchJoin()
                .leftJoin(order.deliveryInfo, delivery).fetchJoin()
                .where(order.orderId.eq(orderId))
                .fetchOne();

        return Optional.of(entity);

    }

    private BooleanExpression searchExpression(String keyword) {
        return keyword == null ? null : orderItem.item.name.containsIgnoreCase(keyword);
    }

}
