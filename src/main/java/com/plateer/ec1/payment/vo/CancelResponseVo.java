package com.plateer.ec1.payment.vo;

import lombok.Data;

@Data
public class CancelResponseVo {
    private String resultCode;
    private String resultMsg;
    private String cancelDate;
    private String cancelTime;
    private String cshrCancelNum;
    private String detailResultCode;
    private String receiptInfo;
}
