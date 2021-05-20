package com.awse.commerce.presentation;

import com.awse.commerce.domains.item.repository.ItemQueryRepository;
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

    private final ItemQueryRepository itemQueryRepository;

    @GetMapping("/")
    public String index(
            @PageableDefault(size = 10, value = 10, sort = "itemId", direction = Sort.Direction.DESC)Pageable pageable
            , String keyword
            , Model model) {
        log.info("index page..");

        model.addAttribute("itemList", itemQueryRepository.findAll(keyword, pageable));

        return "index";
    }
}
