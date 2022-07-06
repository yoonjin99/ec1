package com.plateer.ec1.promotion.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PromotionVo {
    private Long prmNo;
    private String prmNm;
    private int dcVal;
    private int minPurAmt;
    private int maxDcAmt;
    private Long cpnIssNo;
    private int dcPrice;
    private String dcCcd;
    private String cpnKindCd;
    private String maxBenefitYn;
    private String goodsNo;
    private String applyCpnYn;
    private String itemNo;

    // 프로모션 번호 & 상품번호 & 단품번호 같은 경우 중복처리
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PromotionVo other = (PromotionVo) obj;
        return Objects.equals(prmNo, other.prmNo) && Objects.equals(goodsNo, other.goodsNo) && Objects.equals(itemNo, other.itemNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prmNo, goodsNo, itemNo);
    }
}
