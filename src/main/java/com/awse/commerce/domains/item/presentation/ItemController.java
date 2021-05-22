package com.awse.commerce.domains.item.presentation;

import com.awse.commerce.domains.item.service.ItemService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품 하나 조회
    @GetMapping("/item/{itemId}")
    public String getItemPage(@PathVariable("itemId") Long itemId,
                              @CurrentUser Member currentMember,
                              Model model) {
        model.addAttribute("item", itemService.findItem(itemId));

        return "item/itemDetails";
    }

}
