package com.plateer.ec1.common.model.promotion;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CcCpnIssueModel {
    private Long cpnIssNo;
    private String mbrNo;
    private LocalDateTime cpnUseDt;
    private Long orgCpnIssNo;
    private String cpnCertNo;
    private String ordNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private Long prmNo;
}
