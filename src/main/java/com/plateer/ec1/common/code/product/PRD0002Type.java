package com.plateer.ec1.common.code.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PRD0002Type {
    DIRECT("10"),
    NOSHIP("20");

    @Getter
    public String type;

}
