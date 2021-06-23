package com.awse.commerce.domains.cart.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.CheckoutDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.dao.RemoveItemDao;
import com.awse.commerce.domains.cart.dto.CartItemDetailsDto;
import com.awse.commerce.domains.cart.dto.CartListDto;
import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.entity.Cart;
import com.awse.commerce.domains.cart.entity.CartObject;
import com.awse.commerce.domains.cart.exception.CartNotFoundException;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.exception.ItemBadRequestException;
import com.awse.commerce.domains.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    // 장바구니 생성
    public Long createCart(Long memberId) {
        Cart cart = new Cart(memberId);

        return cartRepository.save(cart).getCartId();
    }
    // 장바구니 조회
    public CartListDto getListInCart(Long memberId) {
        // 장바구니의 유무 확인
        Cart cart = cartRepository.findByMemberId(memberId).
                orElseThrow(() -> new CartNotFoundException());
        // 장바구니 목록을 변환시킨다.
        List<CartItemDetailsDto> cartItemList = bindToDto(cart.getCartMap());

        return new CartListDto(cartItemList, cartItemList.size());
    }

    // 주문할 상품 장바구니에서 조회
    public CheckoutItemListDto getCheckoutItems(Long memberId, CheckoutDaoListDto dtoListDto) {
        // 장바구니가 있는 지 찾는다.
        Cart cart = findCartEntity(memberId);
        // 주문할 상품들이 담긴 목록 받기
        List<CartItemDetailsDto> itemDetailsDtoList = bindToCheckoutDto(dtoListDto.getDaoList());

        return new CheckoutItemListDto(itemDetailsDtoList);
    }
    
    // 상품 담기
    public void addToCart(Long memberId, AddRequestItemDao requestItemDao) {
        // 장바구니가 있는 지 찾는다.
        Cart cart = findCartEntity(memberId);
        // 장바구니에 넣을 상품객체를 조립
        CartObject cartObject = new CartObject(cart.getCartId(), requestItemDao.getItemId(), requestItemDao.getOrderCount());
        // 상품의 재고량 확인
        int targetStockQuantity = getStockQuantity(requestItemDao.getItemId());
        // 장바구니에 넣기.
        cart.addToCart(targetStockQuantity, cartObject);
    }

    // 상품 빼기
    public void removeItemInCart(Long memberId, Long itemId) {
        // 해당 사용자의 장바구니찾기
        Cart cart = findCartEntity(memberId);
        // 장바구니에서 삭제할 상품의 id로 삭제하기기
        cart.removeItemInCart(itemId);
    }

    // 상품 선택 삭제
    public void selectRemoveItemsInCart(Long memberId, RemoveItemDao dao) {
        Cart cart = findCartEntity(memberId);

        dao.getItemIdList().forEach(itemId -> {
            cart.removeItemInCart(itemId);
        });
    }

    // 상품 전체 비우기 (주문시 사용)
    public void removeItemsInCart(Long memberId) {
        // 해당 사용자의 장바구니찾기
        Cart cart = findCartEntity(memberId);
        // 장바구니 목록 지우기
        cart.removeItemsInCart();
    }

    // 상품 수정
    public void modifyItemInCart(Long memberId, ModifyRequestItemDao requestItemDao) {
        // 장바구니를 찾는다.
        Cart cart = findCartEntity(memberId);
        // 수정할 상품객체 조립
        CartObject cartObject = new CartObject(cart.getCartId(), requestItemDao.getItemId(), requestItemDao.getOrderCount());
        // 상품 재고량 확인
        int targetStockQuantity = getStockQuantity(requestItemDao.getItemId());
        // 장바구니 수정
        cart.modifyItemCount(targetStockQuantity, cartObject);
    }

    private int getStockQuantity(Long itemId) {
        return itemRepository.findById(itemId).
                orElseThrow(() -> new ItemBadRequestException()).getStockQuantity();
    }

    private Cart findCartEntity(Long memberId) {
        return cartRepository.findByMemberId(memberId).
                orElseThrow(() -> new CartNotFoundException());
    }


    // 장바구니 목록 전체 조회 변환
    private List<CartItemDetailsDto> bindToDto(Map<Long, CartObject> cartMapList) {
        List<CartItemDetailsDto> bindingDto = cartMapList.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(cartMap -> {
            CartObject cartObject = cartMap.getValue();
            Item item = itemRepository.findById(cartObject.getItemId()).get();

            return CartItemDetailsDto.builder()
                    .itemId(cartObject.getItemId())
                    .imgPath(item.getImgPath())
                    .itemAmount(item.getMoney())
                    .itemName(item.getName())
                    .orderCount(cartObject.getOrderCount())
                    .build();

        }).collect(Collectors.toList());

        return bindingDto;
    }

    // 주문할 상품 목록 변환
    // NOTE : 현재 장바구니의 상품이 있는지 체크하고 있지 않다.
    private List<CartItemDetailsDto> bindToCheckoutDto(List<CheckoutDao> daoList) {
        List<CartItemDetailsDto> dtoList = daoList.stream().map(dao -> {
            Item item = itemRepository.findById(dao.getItemId()).get();

            return CartItemDetailsDto.builder()
                    .itemId(dao.getItemId())
                    .itemAmount(item.getMoney())
                    .imgPath(item.getImgPath())
                    .itemName(item.getName())
                    .orderCount(dao.getOrderCount())
                    .build();
        }).collect(Collectors.toList());

        return dtoList;
    }

}
