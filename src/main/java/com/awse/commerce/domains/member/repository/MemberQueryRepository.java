package com.awse.commerce.domains.member.repository;

import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.entity.QMember;
import com.awse.commerce.domains.order.entity.QOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class MemberQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory jpaQueryFactory;

    private QMember member;
    private QOrder order;

    public MemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.member = QMember.member;
        this.order = QOrder.order;
    }

    // 우선 모든메서드는 페이징처리를 뺴고 진행 
    // 변경이 쉽게 QueryResults로 진행
    public List<Member> findAll() {

        return jpaQueryFactory.selectFrom(member)
               .orderBy(member.id.desc()).fetch();
    }

    public List<Tuple> findAllWithOrder() {

        QueryResults<Tuple> queryResults = jpaQueryFactory.select(member, order)
                .from(member)
                .leftJoin(order)
                .on(order.orderer.eq(member))
                .fetchResults();

        return queryResults.getResults();
    }

    public List<Tuple> findMemberByName(String name) {

        QueryResults<Tuple> queryResults  = jpaQueryFactory.select(member, order)
                .from(member)
                .leftJoin(order)
                .on(order.orderer.eq(member))
                .where(member.name.eq(name))
                .fetchResults();

        return queryResults.getResults();
    }
 }
