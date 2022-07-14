package com.plateer.ec1.payment.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ININoticeVo {
    private String len;
    private String noTid;
    private String noOid;
    private String idMerchant;
    private String cdBank;
    private String cdDeal;
    @NotEmpty(message = "dtTrans 값이 없습니다.")
    private String dtTrans;
    @NotEmpty(message = "tmTrans 값이 없습니다.")
    private String tmTrans;
    private String noMsgseq;
    private String cdJoinorg;
    private String dtTransbase;
    private String noTransseq;
    private String typeMsg;
    private String clClose;
    private String noMsgmanage;
    private String noVacct;
    @NotEmpty(message = "amtInput 값이 없습니다.")
    private String amtInput;
    private String amtCheck;
    private String nmInputbank;
    private String nmInput;
    private String dtInputstd;
    private String dtCalculstd;
    private String flgClose;
    @NotEmpty(message = "noReqTid의 값이 없습니다.")
    private String noReqTid;

}
