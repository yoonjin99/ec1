package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OPT0005Type {
    APLY("10"),
    CNCL("20");

    @Getter
    private String type;
}
