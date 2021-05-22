package com.awse.commerce.domains.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
public class PageResultItemDto<DTO> {

    // 페이지에 뿌릴 목록
    private List<DTO> dtoList;

    // 페이지번호를 뿌려줄 변수들 생성
    private int totalPage;
    private int page, size, start, end;

    private boolean prev, next;

    // 페이지 번호 나열용 목록
    private List<Integer> pageList;
    //
    public PageResultItemDto(Page<DTO> dtoPage) {
        dtoList = dtoPage.toList();

        totalPage = dtoPage.getTotalPages();
        makePaging(dtoPage.getPageable());
    }

    public void makePaging(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page * 0.1)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
