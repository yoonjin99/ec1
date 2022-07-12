package com.plateer.ec1.payment.vo;

import lombok.Data;

@Data
public class AccountResponseVo {
    private String resultCode;
    private String resultMsg;
    private String tid;
    private String authDate;
    private String authTime;
    private String vacct;
    private String vacctName;
    private String vacctBankCode;
    private String validDate;
    private String validTime;
    private String inputName;
    private Long price;
    private String refundAcct;
    private String refundBankCode;
    private String refundAcctName;
}
