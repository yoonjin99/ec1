package com.plateer.ec1.order.enums.history;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0012Type {
    S("성공"),
    FD("데이터생성"),
    FV("유효성검사"),
    FP("결제");

    private String cdNm;
}
