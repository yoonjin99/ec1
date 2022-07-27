package com.plateer.ec1.common.code.order.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0010Code {
    PAY("10"),
    CANCEL("20");

    private String type;

    public String getType(){
        return type;
    }
}
