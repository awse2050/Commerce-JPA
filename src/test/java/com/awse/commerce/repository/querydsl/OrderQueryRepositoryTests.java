package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.dto.OrderInfoDto;
import com.awse.commerce.domains.order.repository.OrderQueryRepository;
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
public class OrderQueryRepositoryTests {

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void findAllTest() {
        orderQueryRepository.findAll().forEach(order -> {
            log.info(order.getOrderer());
        });
        //log.info(orderQueryRepository.findAll());
    }

    @Test
    public void findOrderByMemberTest() {
        Member member = memberRepository.findById(2L).get();
        List<OrderInfoDto> list = orderQueryRepository.findOrderByMember(member);

        log.info(list.get(0).getOrderItemCount());
        log.info(list.get(0).getDeliveryStatus());
        log.info(list.get(0).getOrdererName());
        log.info(list.get(0).getOrderId());
        log.info(list.get(0).getOrderStatus());

        Assertions.assertThat(list.get(0).getDeliveryStatus()).isEqualTo("READY");


    }
}
