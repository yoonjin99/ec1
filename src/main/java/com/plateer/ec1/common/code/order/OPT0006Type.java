package com.plateer.ec1.common.code.order;

import lombok.Getter;

public enum OPT0006Type {
    SHIPMENT("10"),
    RETURN("20"),
    EXCHANGE("30");

    OPT0006Type(String type) {
        this.type = type;
    }

    @Getter
    private String type;
}
