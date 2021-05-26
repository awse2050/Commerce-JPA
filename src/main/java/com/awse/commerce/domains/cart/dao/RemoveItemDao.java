package com.awse.commerce.domains.cart.dao;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveItemDao {
    private List<Long> itemIdList = new ArrayList<>();
}
