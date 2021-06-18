package com.awse.commerce.domains.member.service;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
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

    // 회원 수정
    // 1. 컨트롤러로 ModifyMemberDto로 받는다.
    public void modifyMemberInfo(Long memberId, ModifyMemberDto modifyMemberDto) {

    // 4. 해당 데이터를 member에 저장한다.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    // 2. 서비스로 해당 데이터를 던져준다.
    // 3. 패스워드를 인코딩 시킨다.
        modifyMemberDto.setPassword(encode(modifyMemberDto.getPassword()));

        member.updateMemberInfo(modifyMemberDto);

        memberRepository.save(member);

    }

    // DTO -> Entity
    private Member bindToEntity(SignUpRequest dto) {

        return Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encode(dto.getPassword()))
                .phone(dto.getPhone())
                .role(MemberRole.USER)
                .address(new Address(dto))
                .build();
    }

    private String encode(String password) {
        return pwEncoder.encode(password);
    }

}
