package com.awse.commerce.presentation;

import com.awse.commerce.domains.item.service.ItemService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@Log4j2
public class IndexController {

    private final ItemService itemService;

    @GetMapping({"/", "/index"})
    public String index(PageRequestDto pageRequestDto, String keyword,
                        @CurrentUser Member currentMember,
                        Model model) {
        log.warn(currentMember);

        model.addAttribute("pageResult", itemService.findAll(pageRequestDto, keyword));
        model.addAttribute("keyword", keyword);

        return "index";
    }

}
