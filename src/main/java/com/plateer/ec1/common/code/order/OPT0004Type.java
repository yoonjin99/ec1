package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OPT0004Type {
    OS("10"),
    CA("20"),
    CS("30"),
    SS("40"),
    RA("50"),
    EA("60"),
    RW("70");

    @Getter
    private String type;
}
