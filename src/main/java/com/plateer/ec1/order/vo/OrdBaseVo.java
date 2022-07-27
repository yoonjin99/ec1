package com.plateer.ec1.order.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class OrdBaseVo {
    @NotEmpty
    private String mbrNo;
    @NotEmpty
    private String ordTpCd;
    @NotEmpty
    private String ordSysCcd;
    @NotEmpty
    private String ordNm;
    @NotEmpty
    private String ordSellNo; //todo: phone 정규식?
    @NotEmpty
    private String ordAddr;
    @NotEmpty
    private String ordAddrDtl;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
}
