package com.awse.commerce.domains.like.presentation;

import com.awse.commerce.domains.like.dto.LikedItemDetails;
import com.awse.commerce.domains.like.dto.PageResultLikedItemDto;
import com.awse.commerce.domains.like.service.LikeService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/mylike")
    public String getMyLikeList(PageRequestDto pageRequestDto,
                     @CurrentUser Member currentMember,
                     Model model) {

        Long memberId = currentMember.getId();

        PageResultLikedItemDto<LikedItemDetails> pageList
                = likeService.getMyLikeList(memberId, pageRequestDto);

        model.addAttribute("pageResult", pageList);

        return "like/likeDetails";
    }

}
