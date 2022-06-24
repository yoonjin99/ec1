package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.factory.PaymentType;
import lombok.Data;

@Data
public class CancelReqVo {
    private PaymentType paymentType;
}
