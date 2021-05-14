package com.awse.commerce.domains.cart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@Table(name = "cart")
@Entity
public class Cart {
    // 장바구니 번호, 누구의 장바구니, 장바구니에 담을 컬렉션.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    private Long memberId;

    // 장바구니를 담을 컬렉션..
    // 값 타입을 하나이상 저장할때 쓴다. ( 1:N )
    // 키 = itemId
    @ElementCollection
    @CollectionTable(name = "cart_map", joinColumns = @JoinColumn(name = "cart_map_id")) //테이블 이름 및 PK값 이름을 설정
    @MapKeyColumn(name = "map_key") // Map 컬렉션의 키의 칼럼명을 지정
    private Map<Long, CartObject> cartMap = new HashMap<>();

    // 장바구니 생성
    public Cart(Long memberId) {
        this.memberId = memberId;
    }
    // 상품 담기
    public void addToCart(int targetStockQuantity, CartObject cartObject) {
        // 재고량 확인
        isEnoughStockQuantity(targetStockQuantity, cartObject.getOrderCount());
        //
        Long mapKey = cartObject.getItemId();
        // 만약 이미 담겨있는 아이템이라면?
        if(cartMap.containsKey(mapKey)) {
            CartObject obj = cartMap.get(mapKey);
            int resultQuantity = obj.getOrderCount() + cartObject.getOrderCount(); // 기존 수량 + 추가한 수량
            cartMap.replace(mapKey, new CartObject(cartId, cartObject.getItemId(), resultQuantity));
        } else {
            // 아이템을 추가.
            cartMap.put(mapKey, cartObject);
        }
    }
    // 상품 수정 ( 개수 수정 )
    public void modifyItemCount(int targetStockQuantity, CartObject cartObject) {
        // 재고량 확인
        isEnoughStockQuantity(targetStockQuantity, cartObject.getOrderCount());

        this.cartMap.replace(cartObject.getItemId(), cartObject);
    }
    // 상품 빼기
    public void removeInCart() {

    }
    // 상품 재고량 계산
    private void isEnoughStockQuantity(int stockQuantity, int requestQuantity) {
        if (stockQuantity < requestQuantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }
}
