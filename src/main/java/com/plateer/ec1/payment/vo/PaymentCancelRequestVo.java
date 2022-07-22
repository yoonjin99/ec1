package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentCancelRequestVo {
    private PaymentType paymentType;
    @NotEmpty
    private String ordNo;
    @NotEmpty
    private String clmNo;
    @NotNull
    private Long cancelPrice;
    private Long payAmt;
}
