package com.awse.commerce.repository;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.enums.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder pwEncoder;

    @DisplayName("회원 추가 테스트")
    @Test
    @Disabled
    public void insertMemberTest() {
        for(int i = 1; i <= 10; i++) {

            Address address = Address.builder().zipcode("146"+i).extraAddress("시흥시 은계남로 "+i).detailsAddress("904동 10"+i+"호").build();

            Member member = Member.builder()
                    .email("user"+i+"@aaa.com")
                    .name("일반회원"+i)
                    .password(pwEncoder.encode("pw"+i))
                    .phone("010324533"+i)
                    .address(address)
                    .role(MemberRole.USER)
                    .build();

            memberRepository.save(member);
        }

        Assertions.assertThat(memberRepository.count()).isEqualTo(11);
    }

    // Member + Address 데이터가 나타난다.
    @DisplayName("전체 사용자 조회")
    @Test
    public void allSelectTest() {
        List<Member> memberList = memberRepository.findAll();

        memberList.stream().forEach(member -> {
            log.info(member);
            log.info(member.getAddress());
        });
    }


    @DisplayName("이메일로 찾기")
    @Test
    public void findyByEmail() {
        Optional<Member> list = memberRepository.findByEmail("user1@aaa.com");

        Member member = list.get();

        Assertions.assertThat(member).isNotNull();
        log.info(member);
    }

    @DisplayName("회원정보 수정하기")
    @Test
    @Transactional
    @Commit
    public void modifyMemberData() {

        ModifyMemberDto memberDto = ModifyMemberDto.builder()
                .name("하")
                .email("dfkdk@naver.com")
                .phone("01031943333")
                .zipcode("19483")
                .extraAddress("허허허허")
                .detailsAddress("하하호")
                .build();

        Long memberId = 5L;

        Member member = memberRepository.findById(memberId).get();

        member.updateMemberInfo(memberDto);

        memberRepository.save(member);

        Assertions.assertThat(member.getName()).isEqualTo(memberDto.getName());
    }
}
