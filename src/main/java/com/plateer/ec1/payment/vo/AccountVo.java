package com.plateer.ec1.payment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountVo {
    @Builder.Default
    private String type = "Pay";
    @Builder.Default
    private String paymethod = "Vacct";
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime timestamp;
    private String clientIp;
    @Builder.Default
    private String mid = "INIpayTest";
    private String moid;
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDateTime dtInput;
    @JsonFormat(pattern = "HHmm")
    private LocalDateTime tmInput;
    private String ordNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;
    private long price;
    private String bankCode;
    private String nmInput;
}
