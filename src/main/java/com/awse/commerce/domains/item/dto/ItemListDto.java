package com.awse.commerce.domains.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ItemListDto {
    // 목록, 개수
    private List<ItemDetailsDto> dtoList;
    private int total;
}
