package com.awse.commerce.domains.order.dao;

import com.awse.commerce.domains.order.dto.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDao {

    private List<OrderRequestDto> orderRequestDtoList;

}
