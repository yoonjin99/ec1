package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0011 {
    REQUESTPAY("10"),
    SUCCESSPAY("20"),
    CANCEL("30");

    private String type;

    public String getType(){
        return type;
    }
}
