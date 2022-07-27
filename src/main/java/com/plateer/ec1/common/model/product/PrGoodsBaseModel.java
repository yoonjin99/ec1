package com.plateer.ec1.common.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PrGoodsBaseModel {
    private String goodsNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;
    private String prgsStatCd;
    private LocalDateTime sysRegDtime;
    private LocalDateTime sysModDtime;
    private String sysRegrId;
    private String sysModrId;
    private String goodsDlvTpCd;
    private String goodsNo;

}
