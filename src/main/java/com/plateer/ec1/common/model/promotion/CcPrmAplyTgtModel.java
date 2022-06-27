package com.plateer.ec1.common.model.promotion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CcPrmAplyTgtModel {
    private Long aplyTgtSeq;
    private String aplyTgtCcd;
    private String aplyTgtNo;
    private String useYn;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private Long prmNo;
}
