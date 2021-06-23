package com.awse.commerce.service;

import com.awse.commerce.domains.item.dto.ItemDetailsDto;
import com.awse.commerce.domains.item.dto.ItemRequestDto;
import com.awse.commerce.domains.item.dto.PageResultItemDto;
import com.awse.commerce.domains.item.service.ItemService;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import com.awse.commerce.domains.util.s3.S3UploadService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class ItemServiceTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private S3UploadService s3UploadService;

    @DisplayName("상품등록 테스트")
    @Test
    @Transactional
    @Commit
    @Disabled
    public void itemSaveTest() {

//        for(int i=0; i < 101; i++) {

        ItemRequestDto dto = ItemRequestDto.builder()
                .itemAmount(1500)
                .imgPath(s3UploadService.getThumbnailPath("bolpen.jpg"))
                .itemName("볼펜")
                .stockQuantity(10)
                .build();

        Long result = itemService.saveItem(dto);
//        }

 //     Assertions.assertThat(result).isNotEqualTo(null);
    }

    @DisplayName("상품 조회 테스트")
    @Transactional
    @Test
    public void findItemTest() {
        ItemDetailsDto dto = itemService.findItem(1L);

        Assertions.assertThat(dto).isNotNull();
    }

    @DisplayName("상품목록 페이징 테스트")
    @Test
    public void pagingTest() {

        PageRequestDto pageRequestDto = new PageRequestDto(1,10);

        PageResultItemDto<ItemDetailsDto> list = itemService.findAll(pageRequestDto, null);

        Assertions.assertThat(list.getDtoList()).isNotNull();
        list.getDtoList().forEach(i -> log.info(i.getItemId()));
    }
}
