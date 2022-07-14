package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InicisReturnType {
    SUCCESS("0200");

    private String typeMsg;
}
