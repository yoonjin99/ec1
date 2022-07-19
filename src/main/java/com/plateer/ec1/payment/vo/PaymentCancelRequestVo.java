package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentCancelRequestVo {
    private PaymentType paymentType;
    private String ordNo;
    private String clmNo;
    private Long cancelPrice;
    private Long payAmt;
}
