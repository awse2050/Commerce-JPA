package com.awse.commerce.controller.member;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.service.MemberLoginService;
import com.awse.commerce.domains.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

// 테스트를 위해서 Security가 적용된 데이터를 받게한다.
public class WithMockCutstomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    private MemberLoginService memberLoginService;

    // withMockCustomUser에서 가진 이메일로
    // 시큐리티 정보를 찾아내서 반환시킨다.
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser withMockCustomUser) {

        UserDetails principal =
                memberLoginService.loadUserByUsername(withMockCustomUser.email());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        return context;
    }
}