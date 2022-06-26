package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductCouponCalculationImpl implements Calculation {
    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        log.info("-------ProductCouponCalculation getCalculationData start");
        return null;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.productCoupon;
    }

    private List<ProductCouponsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------ProductCouponCalculation getAvailablePromotionData start");
        return null;
    }

    private List<ProductCouponsVo> calculateDcAmt(PromotionRequestVo vo, PromotionVo prm){
        log.info("-------ProductCouponCalculation calculate start");
        return null;
    }

    private ProductCouponResponseVo calculateMaxBenefit(PromotionRequestVo vo){
        log.info("-------ProductCouponCalculation calculateMaxBenefit start");
        return new ProductCouponResponseVo();
    }
}
