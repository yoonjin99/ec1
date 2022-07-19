package com.plateer.ec1.payment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CancelRequestVo {
    private PaymentType paymentType;
    private String type;
    private String paymethod;
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime timestamp;
    private String clientIp;
    private String mid;
    private String tid;
    private String msg;
    private Long price;
    private Long confirmPrice;
    private String refundAcctNum;
    private String refundBankCode;
    private String refundAcctName;
    private String hashData;
}
