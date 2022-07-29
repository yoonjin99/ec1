package com.plateer.ec1.common.code.product;

import lombok.Getter;

public enum PRD0001Type {
    GENERAL("10"),
    ECOUPON("20");

    PRD0001Type(String type) {
        this.type = type;
    }

    @Getter
    String type;
}
