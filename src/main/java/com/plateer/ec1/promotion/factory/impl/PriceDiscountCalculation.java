package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PriceDiscountCalculation implements Calculation {
    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        log.info("-------PriceDiscountCalculation getCalculationData start");
        return null;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.priceDiscount;
    }

    private List<ProductCouponsVo> getAvailablePromotionData(PromotionRequestVo vo){
        log.info("-------PriceDiscountCalculation getAvailablePromotionData start");
        return null;
    }

    private PriceDiscountResponseVo calculateDcAmt(PromotionRequestVo reqVo, PromotionVo prm){
        log.info("-------PriceDiscountCalculation calculateDcAmt start");
        return null;
    }
}
