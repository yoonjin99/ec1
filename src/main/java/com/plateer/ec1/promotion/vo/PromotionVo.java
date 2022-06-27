package com.plateer.ec1.promotion.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionVo {
    private Long prmNo;
    private String prmNm;

    private String cpnKindCd;
    private Long couponIssueNo;
    private String degrCcd;
    private Long dcAmt;
    private String maxBenefitYn;
}
