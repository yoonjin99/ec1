package com.plateer.ec1.payment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PartCancelResponseVo {
    private String resultCode;
    private String resultMsg;
    private String tid;
    private String prtcTid;
    private Long prtcPrice;
    private Long prtcRemains;
    private Long prtcCnt;
}
