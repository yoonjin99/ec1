package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InicisUrlType {
    PAYMENT_URL("https://iniapi.inicis.com/api/v1/formpay"),
    REFUND_URL("https://iniapi.inicis.com/api/v1/refund");

    public String url;
}
