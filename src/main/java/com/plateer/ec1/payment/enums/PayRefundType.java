package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayRefundType {
    PAY("Pay"),
    REFUND("Refund"),
    PARTREFUND("PartialRefund");

    public String type;
}
