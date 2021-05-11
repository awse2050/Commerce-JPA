package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.entity.QMember;
import com.awse.commerce.domains.member.repository.MemberQueryRepository;
import com.awse.commerce.domains.order.entity.Order;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Log4j2
@Transactional
public class MemberQueryRepositoryTests {

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Test
    public void findAllTest() {

        List<Member> list = memberQueryRepository.findAll();

        Assertions.assertThat(list.size()).isEqualTo(9);
    }

    @Test
    public void findAllWithOrderTest() {
        List<Tuple> list = memberQueryRepository.findAllWithOrder();

        Tuple tuple = list.get(0);
        log.info(tuple);
    }

    @Test
    public void findMemberByNameTest() {

        List<Tuple> list = memberQueryRepository.findMemberByName("일반회원8");

        Tuple tuple = list.get(0);

        Object obj = (Object) tuple;

        Assertions.assertThat(list.size()).isEqualTo(1);

    }
}