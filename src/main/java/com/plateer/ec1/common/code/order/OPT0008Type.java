package com.plateer.ec1.common.code.order;

import lombok.Getter;

public enum OPT0008Type {
    CONSUMER("10"),
    COMPANY("20");

    OPT0008Type(String type) {
        this.type = type;
    }

    @Getter
    private String type;
}
