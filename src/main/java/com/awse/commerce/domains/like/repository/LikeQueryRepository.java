package com.awse.commerce.domains.like.repository;

import com.awse.commerce.domains.item.entity.QItem;
import com.awse.commerce.domains.like.dto.LikedItemDetails;
import com.awse.commerce.domains.like.dto.QLikedItemDetails;
import com.awse.commerce.domains.like.entity.Like;
import com.awse.commerce.domains.like.entity.QLike;
import com.awse.commerce.domains.member.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    private QItem item;
    private QLike like;
    private QMember member;

    public LikeQueryRepository(JPAQueryFactory queryFactory) {
        super(Like.class);
        this.queryFactory = queryFactory;
        this.item = QItem.item;
        this.like = QLike.like;
        this.member= QMember.member;
    }

    // 특정 사용자의 찜 목록 조회 ( 마이페이지 )
    public Page<LikedItemDetails> getLikeList(Long memberId, Pageable pageable) {

        QueryResults<LikedItemDetails> queryResults = queryFactory
                .select(new QLikedItemDetails(
                        item.itemId,
                        item.name,
                        item.imgPath,
                        item.money,
                        item.likes.size()
                )).from(like)
                    .leftJoin(like.member, member)
                    .leftJoin(like.item, item)
                    .where(member.id.eq(memberId))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(like.id.desc())
                    .fetchResults();

        List<LikedItemDetails> result = queryResults.getResults();

        long total = queryResults.getTotal();

        return new PageImpl<>(result, pageable, total);
    }

}
