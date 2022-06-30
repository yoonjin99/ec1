package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;


@Setter
@Getter
@ToString
public class ProductCouponsVo {
    private String goodsNo;
    private int prdTotCnt;
    private int prdPrice;
    private Long prmNo;
    private String prmNm;
    private int dcVal;
    private int minPurAmt;
    private int maxDcAmt;
    private Long cpnIssNo;
    private int dcPrice;
    private String dcCcd;
    private String maxBenefitYn;
    private String cpbKindCd;
    private String mbrNo;

    // 프로모션 번호 & 상품번호 같은 경우 중복처리
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductCouponsVo other = (ProductCouponsVo) obj;
        return Objects.equals(prmNo, other.prmNo) && Objects.equals(goodsNo, other.goodsNo);
    }

}
