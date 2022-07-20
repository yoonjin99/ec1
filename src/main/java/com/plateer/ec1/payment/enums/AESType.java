package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AESType {
    SECRETKEY("ItEQKi3rY7uvDS8l"),
    IV("HYb3yQ4f65QL89==");

    public String str;
}
