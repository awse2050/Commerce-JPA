package com.awse.commerce.domains.member.service;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    // 회원등록
    public Long signUp(SignUpRequest signUpRequest) {

        // 컨트롤러에서 해당 비밀번호가 일치하는지에 대한 처리를 한다?
        // 또는 여기서 한다.
        Member member = bindToEntity(signUpRequest);

        Long resultId = memberRepository.save(member).getId();

        return resultId;
    }

    // DTO -> Entity
    private Member bindToEntity(SignUpRequest dto) {

        return Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encode(dto.getPassword()))
                .role(MemberRole.USER)
                .address(new Address(dto))
                .build();
    }

    private String encode(String password) {
        return pwEncoder.encode(password);
    }

}
