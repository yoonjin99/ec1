package com.plateer.ec1.common.code.order.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0011Code {
    REQUESTPAY("10"),
    SUCCESSPAY("20"),
    CANCEL("30");

    private String type;

    public String getType(){
        return type;
    }
}
