package com.plateer.ec1.common.model.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpOrdBaseModel {
    private String ordNo;
    private String mbrNo;
    private String ordTpCd;
    private String ordSysCcd;
    private LocalDateTime ordReqDtime;
    private LocalDateTime ordCmtDtime;
    private String ordNm;
    private String ordSellNo;
    private String ordAddr;
    private String ordAddrDtl;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModrId;
    private String sysModDtim;

}
