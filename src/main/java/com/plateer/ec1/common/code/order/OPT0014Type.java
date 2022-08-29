package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OPT0014Type {
    DVP("10"),
    RETURN("20");

    @Getter
    private String type;
}
