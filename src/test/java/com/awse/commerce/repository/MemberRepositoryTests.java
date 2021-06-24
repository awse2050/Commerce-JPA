package com.awse.commerce.repository;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.exception.MemberBadRequestException;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder pwEncoder;

    @DisplayName("회원 추가 테스트")
    @Test
    @Order(1)
    public void insertMemberTest() {

        Address address = Address.builder().zipcode("146").extraAddress("시흥시 은계남로 ").detailsAddress("904동 10" +  "호").build();

        Member member = Member.builder()
                .email("test1234@aaa.com")
                .name("일반회원" )
                .password(pwEncoder.encode("pw"))
                .phone("010324533")
                .address(address)
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findByEmail("test1234@aaa.com").isPresent()).isTrue();
    }

    // Member + Address 데이터가 나타난다.
    @DisplayName("전체 사용자 조회")
    @Test
    @Order(2)
    public void allSelectTest() {
        List<Member> memberList = memberRepository.findAll();

        Assertions.assertThat(memberList.size()).isGreaterThan(0);
    }


    @DisplayName("이메일로 찾기")
    @Test
    @Order(3)
    public void findyByEmail() {
        Optional<Member> list = memberRepository.findByEmail("test1234@aaa.com");

        Member member = list.get();

        Assertions.assertThat(member).isNotNull();
        log.info(member);
    }

    @DisplayName("회원정보 수정하기")
    @Test
    @Transactional
    @Commit
    @Order(4)
    public void modifyMemberData() {

        ModifyMemberDto memberDto = ModifyMemberDto.builder()
                .name("하")
                .email("test1234@aaa.com")
                .phone("01031943333")
                .zipcode("19483")
                .extraAddress("허허허허")
                .detailsAddress("하하호")
                .build();

        Long memberId = memberRepository.findByEmail("test1234@aaa.com").
                orElseThrow(() -> new MemberBadRequestException()).getId();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberBadRequestException());

        member.updateMemberInfo(memberDto);

        memberRepository.save(member);

        Assertions.assertThat(member.getName()).isEqualTo(memberDto.getName());
        Assertions.assertThat(member.getPhone()).isEqualTo(memberDto.getPhone());
    }

    @DisplayName("회원 삭제")
    @Test
    @Commit
    @Order(5)
    public void deleteMemberTest() {
        Member member = memberRepository.findByEmail("test1234@aaa.com").
                orElseThrow(() -> new MemberBadRequestException());

        Long memberId = member.getId();

        memberRepository.deleteById(memberId);

        Assertions.assertThat(memberRepository.findById(memberId).isPresent()).isFalse();
    }
}
