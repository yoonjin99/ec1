package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0010 {
    PAY("10"),
    CANCEL("20");

    private String type;

    public String getType(){
        return type;
    }
}
