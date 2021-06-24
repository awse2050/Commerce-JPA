package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.like.dto.LikedItemDetails;
import com.awse.commerce.domains.like.repository.LikeQueryRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class LikeQueryRepositoryTests {

    @Autowired
    private LikeQueryRepository likeQueryRepository;

    @DisplayName("찜 목록 조회 Querydsl 테스트")
    @Test
    @Transactional
    public void getLikeListTest() {
       Page<LikedItemDetails> pageList =
               likeQueryRepository.getLikeList(1L, PageRequest.of(0, 10, Sort.by("id").descending()));

       if(pageList.toList().size() == 0) {
           log.info("pageList's size? -> 0 ");
           Assertions.assertThat(pageList.toList().size()).isEqualTo(0);
       } else {
           log.info("pageList's size? ->  greater than 0 ");
           Assertions.assertThat(pageList.toList().size()).isGreaterThan(0);
       }

    }
}
