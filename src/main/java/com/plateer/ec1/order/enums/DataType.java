package com.plateer.ec1.order.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum DataType {
    GENERAL("general"),
    ECOUPON("ecoupon");

    private String type;

    public static DataType getDataType(String orderType){
        return Arrays.stream(values())
                .filter((t) -> t.type.equals(orderType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
