package com.awse.commerce.domains.util.enums;

import javax.persistence.Embeddable;

@Embeddable
public enum OrderStatus {
    ORDERED, SHIPPING, CANCEL, COMPLETE
}
