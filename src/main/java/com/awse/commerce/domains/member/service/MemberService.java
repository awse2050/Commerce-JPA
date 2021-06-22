package com.awse.commerce.domains.member.service;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.ModifyPasswordDto;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.exception.MemberBadRequestException;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
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

        Member member =  findMemberEntity(memberId);

        member.updateMemberInfo(modifyMemberDto);

        memberRepository.save(member);

    }

    // 회원 찾기 ( myinfo )
    public Member getMember(Long memberId) {
        return findMemberEntity(memberId);
    }

    // 비밀번호 수정
    public void changePassword(Long memberId, ModifyPasswordDto passwordDto) {
        Member member = findMemberEntity(memberId);

        boolean isEqualResult =  isEqualsPassword(passwordDto.getCurrentPassword(), member.getPassword());
        boolean equalsToModifyPassword = equalsToModifyPassword(passwordDto.getToModifyPassword(), passwordDto.getConfirmPassword());

        if(isEqualResult && equalsToModifyPassword) {
            member.changePassword(encode(passwordDto.getToModifyPassword()));
            memberRepository.save(member);
        } else if(!isEqualResult) {
            throw new MemberBadRequestException("현재 비밀번호가 일치하지 않습니다.");
        } else if(!equalsToModifyPassword) {
            throw new MemberBadRequestException("변경 할 비밀번호가 서로 일치하지 않습니다.");
        }
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

    private boolean isEqualsPassword(String requestPwd, String initPwd) {
        return pwEncoder.matches(requestPwd, initPwd);
    }

    private boolean equalsToModifyPassword(String toModPwd, String confirmPwd) {
        return toModPwd.equals(confirmPwd);
    }

    private Member findMemberEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberBadRequestException("존재하지 않는 사용자입니다."));
    }
}
