package com.awse.commerce.domains.cart.dto;

import com.awse.commerce.domains.cart.dao.CheckoutDao;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CheckoutDaoListDto {
    private List<CheckoutDao> daoList;
}
