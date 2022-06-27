package com.plateer.ec1.common.model.promotion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CcMbrPntModel {
    private Long pntHstSeq;
    private String mbrNo;
    private String svUseCcd;
    private Double svUseAmt;
    private Double pntBlc;
    private String ordNo;
    private String payNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

}
