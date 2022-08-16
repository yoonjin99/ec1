package com.plateer.ec1.common.code.order;

import lombok.Getter;

@Getter
public enum OPT0012Type {
    S("성공"),
    FD("데이터 생성 오류 발생"),
    FV("유효성 검사 오류 발생"),
    FP("결제 오류 발생");

    private String desc;

    OPT0012Type(String desc) {
        this.desc = desc;
    }
}
