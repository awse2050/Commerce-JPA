package com.awse.commerce.repository;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.delivery.repository.DeliveryRepository;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class DeliveryRepositoryTests {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @DisplayName("배송지 변경")
    @Test
    public void deliveryChangeTest() {
        Delivery delivery = deliveryRepository.findById(1L).get();

        delivery.changeDeliveryStatus(DeliveryStatus.READY);

        deliveryRepository.save(delivery);

        Assertions.assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
    }

}
