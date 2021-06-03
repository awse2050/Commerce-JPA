package com.awse.commerce.domains.like.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@ToString
public class LikedItemDetails {
    // 상품 번호, 이름, 가격, 좋아요 개수
    private Long likedItemId;
    private String likedItemName;
    private int likedItemAmount;

    private int likeCount;

    // Querydsl로 조회하게 한다.
    @QueryProjection
    public LikedItemDetails(Long itemId, String itemName, int itemAmount, int likeCount) {
        this.likedItemId = itemId;
        this.likedItemName = itemName;
        this.likedItemAmount = itemAmount;
        this.likeCount = likeCount;
    }



}
