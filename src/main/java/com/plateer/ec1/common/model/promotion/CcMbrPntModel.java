package com.plateer.ec1.common.model.promotion;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CcMbrPntModel {
    private Long pntHstSeq;
    private String mbrNo;
    private String svUseCcd;
    private Long svUseAmt;
    private Long pntBlc;
    private String ordNo;
    private String payNo;
    private LocalDateTime sysRegDtime;
    @Builder.Default
    private String sysRegrId = "admin";
    private LocalDateTime sysModDtime;
    @Builder.Default
    private String sysModrId = "admin";

}
