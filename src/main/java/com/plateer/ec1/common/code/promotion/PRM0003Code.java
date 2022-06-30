package com.plateer.ec1.common.code.promotion;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PRM0003Code {
    PRICE("10"),
    PERCENT("20");

    private String type;

    public String getType(){
        return type;
    }
}
