package com.awse.commerce.domains.like.presentation;

import com.awse.commerce.domains.like.service.LikeService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class LikeApiController {

    private final LikeService likeService;

    private static final String API_URI = "/api/like";

    // 찜 추가
    @PostMapping(API_URI+"/{itemId}")
    public ResponseEntity<String> addLike(@PathVariable("itemId") Long itemId,
                                  @CurrentUser Member currentMember) {
        if(currentMember == null) {
            return new ResponseEntity<>("로그인이 필요한 서비스 입니다.", HttpStatus.BAD_REQUEST);
        }
        log.info("add Like ItemId : "+ itemId);

        boolean isAddLike = likeService.addLike(currentMember, itemId);

        return isAddLike ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 찜 삭제
    @DeleteMapping(API_URI+"/{itemId}")
    public ResponseEntity<String> cancelLike(@PathVariable("itemId")Long itemId,
                           @CurrentUser Member currentMember) {
        if(currentMember == null) {
            return new ResponseEntity<>("로그인이 필요한 서비스 입니다.", HttpStatus.BAD_REQUEST);
        }

        log.info("cancel Like ItemId : "+ itemId);
        likeService.cancelLike(currentMember, itemId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
