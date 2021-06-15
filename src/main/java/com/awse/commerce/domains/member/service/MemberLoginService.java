package com.awse.commerce.domains.member.service;

import com.awse.commerce.domains.member.dto.MemberDto;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.warn("login email : " + email);
        // 이메일을 가진 회원을 찾는다.
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정 입니다."));

        log.warn("member : " +member);
        // 해당 데이터를 변환시켜서 보내준다.
        return new MemberDto(member);
    }
}
