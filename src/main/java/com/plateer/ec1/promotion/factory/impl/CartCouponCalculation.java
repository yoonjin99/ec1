package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CartCouponCalculation implements Calculation {
    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        log.info("-------CartCouponCalculation getCalculationData start");
        return null;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.cartCoupon;
    }

    private List<CouponProductsVo> getAvailablePromotionData(PromotionRequestVo vo){
        log.info("-------CartCouponCalculation getAvailablePromotionData start");
        return null;
    }

    private List<CouponProductsVo> calculateDcAmt(PromotionRequestVo vo, PromotionVo prm){
        log.info("-------CartCouponCalculation calculateDcAmt start");
        return null;
    }

    private List<CouponProductsVo> calculateMaxBenefit(CartCouponResponseVo vo){
        log.info("-------CartCouponCalculation calculateMaxBenefit start");
        return null;
    }
}
