package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.item.dto.ItemDetailsDto;
import com.awse.commerce.domains.item.dto.ItemListDto;
import com.awse.commerce.domains.item.repository.ItemQueryRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class ItemQueryRepositoryTests {

    @Autowired
    private ItemQueryRepository itemQueryRepository;


    @DisplayName("상품전체 조회 테스트")
    @Test
    public void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("itemId").descending());

        Page<ItemDetailsDto> pageList = itemQueryRepository.findAll(null, pageable);

        pageList.getContent().forEach(i -> log.info(i.getLikeCount()));
        Assertions.assertThat(pageList.getContent()).isNotNull();
        Assertions.assertThat(pageList.getContent().size()).isEqualTo(2);

    }
}
