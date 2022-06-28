package com.plateer.ec1.common.model.promotion;

import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public class CcCpnIssueModel {
    private Long cpnIssNo;
    private String mbrNo;
    private LocalDateTime cpnUseDt;
    private Long orgCpnIssNo;
    private String cpnCertNo;
    private String ordNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysRegDtime;
    @Builder.Default
    private String sysRegrId = "admin";
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysModDtime;
    @Builder.Default
    private String sysModrId = "admin";
    private Long prmNo;
}
