package com.plateer.ec1.common.code.order;

import lombok.Getter;

public enum DVP0001Type {
    CHARGED("10"),
    FREE("20"),
    CASH("30");

    DVP0001Type(String type) {
        this.type = type;
    }

    @Getter
    private String type;
}
