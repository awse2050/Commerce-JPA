package com.awse.commerce.domains.order.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 나의 주문의 정보를 전달할때 주문상품목록을 만드는 DTO
public class OrderItemDetailsDto {
    // 주문상품번호
    private Long orderItemId;
    // 주문한 해당 상품의 개수
    private int orderCount;
    // 주문한 해당 상품의 총액
    private int orderItemAmount;
    // 주문한 상품에 대한 정보.
    // 상품명
    private String name;
    // 이미지
    private String imgPath;
}
