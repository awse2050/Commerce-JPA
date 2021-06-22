package com.awse.commerce.domains.item.service;

import com.awse.commerce.domains.item.dto.ItemDetailsDto;
import com.awse.commerce.domains.item.dto.ItemRequestDto;
import com.awse.commerce.domains.item.dto.PageResultItemDto;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.exception.ItemBadRequestException;
import com.awse.commerce.domains.item.repository.ItemQueryRepository;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;

    // 상품 등록
    @CacheEvict(value = "findEntityCache")
    @Transactional // 조회성능이 아닌 일반 트랜잭션을 이용하게 한다.
    public Long saveItem(ItemRequestDto itemRequestDto) {
        // to Entity
        Item item = ItemRequestDto.getItemEntity(itemRequestDto);
        // save
        Long result = itemRepository.save(item).getItemId();

        return result;
    }

    // 상품 조회
    public ItemDetailsDto findItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemBadRequestException());

        return new ItemDetailsDto(item);
    }

    // 상품 전체 조회
    @Cacheable(value = "findEntityCache")
    public PageResultItemDto<ItemDetailsDto> findAll(PageRequestDto requestDto, String keyword) {
        // Querydsl 로 조회한 데이터를 가져온다. 이때 PageRequestDto의 데이터로 Pageable을 구현시킴.
        Page<ItemDetailsDto> itemDetailsDtos = itemQueryRepository.findAll(keyword, requestDto.getPageable("itemId"));
        // 이후 해당 데이터를 PageResult로 반환

        return new PageResultItemDto<>(itemDetailsDtos);
    }
}
