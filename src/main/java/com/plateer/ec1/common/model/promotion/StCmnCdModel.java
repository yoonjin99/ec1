package com.plateer.ec1.common.model.promotion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StCmnCdModel {
    private String cmnGrpCd;
    private String cmnCd;
    private String cmnGrpCdNm;
    private String cmnCdNm;
    private String ref1Val;
    private String ref2Val;
    private String useYn;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
}
