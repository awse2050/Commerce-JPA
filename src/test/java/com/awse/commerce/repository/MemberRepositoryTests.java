package com.awse.commerce.repository;

import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.embedded.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 추가 테스트")
    @Test
    public void insertMemberTest() {
        for(int i = 1; i <= 9; i++) {

            Address address = Address.builder().zipcode("1451"+i).extraAddress("시흥시 은계남로 "+i).detailsAddress("904동 100"+i+"호").build();

            Member member = Member.builder()
                    .email("user"+i+"@aaa.com")
                    .name("일반회원"+i)
                    .password("pw"+i)
                    .address(address)
                    .build();

            memberRepository.save(member);
        }

        Assertions.assertThat(memberRepository.count()).isEqualTo(9);
    }

}
