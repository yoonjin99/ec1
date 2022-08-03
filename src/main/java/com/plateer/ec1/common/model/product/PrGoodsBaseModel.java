package com.plateer.ec1.common.model.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
