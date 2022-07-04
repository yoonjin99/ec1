package com.plateer.ec1.promotion.vo;

import com.plateer.ec1.common.code.promotion.PRM0003Code;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
public class ProductCouponsVo {
    private ProductVo productVo;
    private List<PromotionVo> promotionVoList;

    public void calculatePrice(ProductCouponsVo prd){
        prd.promotionVoList.forEach(vo -> {
            int dcPrice = PRM0003Code.PRICE.getType().equals(vo.getDcCcd()) ? vo.getDcVal() : (int) (prd.productVo.getPrdPrice() * (vo.getDcVal() * 0.01));
            vo.setDcPrice(Math.min(dcPrice, vo.getMaxDcAmt()));
        });
    }
}
