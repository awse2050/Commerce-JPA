package com.awse.commerce.repository;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.delivery.repository.DeliveryRepository;
import com.awse.commerce.domains.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryRepositoryTests {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("배송지 추가 및 확인")
    @Test
    public void update1() {
        Delivery delivery = deliveryRepository.findById(1L).get();

//        delivery.changeAddress(memberRepository.findById(1L).get().getAddress());

//        deliveryRepository.save(delivery);

        Assertions.assertThat(delivery.getAddress().getZipcode()).isNotNull();

    }


}
