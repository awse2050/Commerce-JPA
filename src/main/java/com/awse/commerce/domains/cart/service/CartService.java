package com.awse.commerce.domains.cart.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.entity.Cart;
import com.awse.commerce.domains.cart.entity.CartObject;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    // 조회 쿼리 주입
    
    // 장바구니 생성
    public Long createCart(Long memberId) {
        Cart cart = new Cart(memberId);

        return cartRepository.save(cart).getCartId();
    }
    // 장바구니 조회
    
    // 상품 담기
    public void addToCart(Long memberId, AddRequestItemDao requestItemDao) {
        // 장바구니가 있는 지 찾는다.
        Cart cart = cartRepository.findByMemberId(memberId).get();
        // 장바구니에 넣을 상품객체를 조립
        CartObject cartObject = new CartObject(cart.getCartId(), requestItemDao.getItemId(), requestItemDao.getOrderCount());
        // 상품의 재고량 확인
        int targetStockQuantity = itemRepository.findById(requestItemDao.getItemId()).get().getStockQuantity();
        // 장바구니에 넣기.
        cart.addToCart(targetStockQuantity, cartObject);
    }
    // 상품 빼기

    // 상품 수정
    public void modifyItemInCart(Long memberId, ModifyRequestItemDao requestItemDao) {
        // 장바구니를 찾는다.
        Cart cart = cartRepository.findById(memberId).get();
        // 수정할 상품객체 조립
        CartObject cartObject = new CartObject(cart.getCartId(), requestItemDao.getItemId(), requestItemDao.getOrderCount());
        // 상품 재고량 확인
        int targetStockQuantity = itemRepository.findById(requestItemDao.getItemId()).get().getStockQuantity();
        // 장바구니 수정
        cart.modifyItemCount(targetStockQuantity, cartObject);
    }
}
