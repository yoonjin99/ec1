package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentType {
    INICIS("10"),
    POINT("20");

    @Getter
    private String type;
}

