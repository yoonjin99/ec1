package com.plateer.ec1.promotion.vo;

import com.plateer.ec1.common.code.promotion.PRM0003Code;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Override
    public int hashCode() {
        return Objects.hash(prmNo, goodsNo);
    }

    public void setterPrdTotCnt(PromotionRequestVo requestVo){
        for(ProductVo vo : requestVo.getProducts()){
            if(Objects.equals(vo.getGoodsNo(), getGoodsNo())){
                setPrdTotCnt(vo.getPrdTotCnt());
            }
        }
    }

    public void calculatePrice(ProductCouponsVo prd){
        int dcPrice = PRM0003Code.PRICE.getType().equals(prd.getDcCcd()) ? prd.getDcVal() : (int) (prd.getPrdPrice() * (prd.getDcVal() * 0.01));
        setDcPrice(Math.min(dcPrice, prd.getMaxDcAmt()) * prd.getPrdTotCnt());
    }
}
