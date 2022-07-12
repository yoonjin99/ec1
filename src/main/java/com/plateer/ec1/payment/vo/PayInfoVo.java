package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PayInfoVo {
    private PaymentType paymentType;
    @NotNull
    private long price;
    @NotNull
    private String bankCode;
    @NotNull
    private String nmInput;
}
