package com.plateer.ec1.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum PGIPType {
    IP_1("203.238.37"),
    IP_2("39.115.212"),
    IP_3("183.109.71");

    private String ip;
}
