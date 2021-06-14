package com.awse.commerce.domains.util.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
public class PageRequestDto {

    // 페이지 갯수 사이즈
    private int page;
    private int size;

    // 기본값으로 1페이지 10개를 보여주자.
    public PageRequestDto() {
        this.page = 1;
        this.size = 12;
    }

    public Pageable getPageable(String sortBy) {
        return PageRequest.of(this.page - 1 , size, setSortBy(sortBy));
    }

    private Sort setSortBy(String sortBy) {
        return Sort.by(sortBy).descending();
    }
}