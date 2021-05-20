package com.awse.commerce.presentation;

import com.awse.commerce.domains.item.repository.ItemQueryRepository;
import com.awse.commerce.domains.item.service.ItemService;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class IndexController {

    private final ItemService itemService;

    @GetMapping("/")
    public String index(PageRequestDto pageRequestDto, String keyword, Model model) {

        model.addAttribute("pageResult", itemService.findAll(pageRequestDto, keyword));

        return "index";
    }
}
