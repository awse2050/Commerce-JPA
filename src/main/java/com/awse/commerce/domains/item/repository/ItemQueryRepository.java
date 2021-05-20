package com.awse.commerce.domains.item.repository;

import com.awse.commerce.domains.item.dto.ItemDetailsDto;
import com.awse.commerce.domains.item.dto.QItemDetailsDto;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.entity.QItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class ItemQueryRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;
    private QItem item;

    public ItemQueryRepository(JPAQueryFactory queryFactory) {
        super(Item.class);
        this.queryFactory = queryFactory;
        this.item = QItem.item;
    }

    // 상품 전체 조회
    public Page<ItemDetailsDto> findAll(String keyword, Pageable pageable) {
        QueryResults<ItemDetailsDto> queryResults =
                queryFactory.select(new QItemDetailsDto(
                item.itemId,
                item.name,
                item.imgPath,
                item.money,
                item.stockQuantity
                )).from(item)
                        .where(searchExpression(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.itemId.desc())
                .fetchResults();

        List<ItemDetailsDto> list = queryResults.getResults();

        long totalCount = queryResults.getTotal();

        return new PageImpl<>(list, pageable, totalCount);
    }

    private BooleanExpression searchExpression(String keyword) {
        return keyword == null ? null : item.name.containsIgnoreCase(keyword);
    }
}
