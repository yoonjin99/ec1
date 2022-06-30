package com.plateer.ec1.promotion.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionVo {
    private Long prmNo;
    private String prmNm;
    private int dcVal;
    private int minPurAmt;
    private int maxDcAmt;
    private Long cpsIssNo;
    private int dcPrice;
    private String dcCcd;
    private String maxBenefitYn;
}
