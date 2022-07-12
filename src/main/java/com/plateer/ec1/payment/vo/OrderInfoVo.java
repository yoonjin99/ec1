package com.plateer.ec1.payment.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class OrderInfoVo {
    @NotNull(message = "주문번호가 null 입니다.")
    private String ordNo;
    @NotNull
    private String goodName;
    @NotNull
    private String buyerName;
    @NotNull
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String buyerEmail;
}
