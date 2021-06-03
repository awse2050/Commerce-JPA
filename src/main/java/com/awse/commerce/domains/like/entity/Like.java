package com.awse.commerce.domains.like.entity;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "item"})
@Table(name = "likes")
@Entity
public class Like {
    // 찜목록 고유번호, 사용자, 상품,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    public Like(Member member, Item item) {
        this.member = member;
        this.item = item;
    }

}
