package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Data;

@Data
public class PayInfoVo {
    private PaymentType paymentType;
    private long price = 1;
    private String bankCode = "03";
    private String nmInput = "배윤진";
}
