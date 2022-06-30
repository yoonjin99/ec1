package com.plateer.ec1.common.code.promotion;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PRM0004Code {
    PRODUCT("10"),
    DUPLICATE("20"),
    BASKET("30");

    private String type;

    public String getType(){
        return type;
    }
}
