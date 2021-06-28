package com.awse.commerce.domains.delivery.entity;

import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.entity.BaseEntity;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@ToString
@Entity
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    // 첫 주문시 사용
    public Delivery(Address address) {
        this.address = address;
        this.deliveryStatus = DeliveryStatus.READY;
    }

    // 배송상태 변경
    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // 주문상세 내역용 배송지
    public String getDeliveryAddress() {
        return this.address.getZipcode() + " "
                + this.address.getExtraAddress() + " "
                + this.address.getDetailsAddress();
    }
}
