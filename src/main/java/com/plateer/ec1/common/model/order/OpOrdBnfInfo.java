package com.plateer.ec1.common.model.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpOrdBnfInfo {
    private String ordBnfNo;
    private Integer ordBnfAmt;
    private Long prmNo;
    private Long cpnIssNo;
    private Integer ordCnclBnfAmt;
    private Integer degrCcd;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String cpnKndCd;
}
