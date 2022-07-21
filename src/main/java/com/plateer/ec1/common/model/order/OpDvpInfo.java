package com.plateer.ec1.common.model.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpDvpInfo {
    private Integer dvGrpNo;
    private String ordNo;
    private Integer dvpSeq;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String dvMthdCd;
}
