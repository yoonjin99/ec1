package com.plateer.ec1.claim.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CreatorType {
    C("C"),
    R("R"),
    RW("RW"),
    EX("EX");

    @Getter
    private String type;
}
