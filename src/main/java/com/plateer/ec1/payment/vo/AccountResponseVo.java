package com.plateer.ec1.payment.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
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
    private String price;
}
