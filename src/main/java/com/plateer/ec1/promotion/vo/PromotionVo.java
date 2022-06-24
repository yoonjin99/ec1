package com.plateer.ec1.promotion.vo;

import lombok.Data;

@Data
public class PromotionVo {
    private Long promotionNo;
    private String cpnKindCd;
    private Long couponIssueNo;
    private String degrCcd;
    private Long dcAmt;
    private String maxBenefitYn;
}
