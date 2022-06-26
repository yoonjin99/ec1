package com.plateer.ec1.promotion.service;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.CalculationFactory;
import com.plateer.ec1.promotion.vo.CartCouponResponseVo;
import com.plateer.ec1.promotion.vo.PriceDiscountResponseVo;
import com.plateer.ec1.promotion.vo.ProductCouponResponseVo;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CalculationFactory calculationFactory;

    public PriceDiscountResponseVo getPriceDiscountApplyData(PromotionRequestVo vo){
        log.info("----------PromotionService getPriceDiscountApplyData start");
        return (PriceDiscountResponseVo) calculationFactory.getPromotionCalculationData(vo, PromotionType.priceDiscount);
    }

    public ProductCouponResponseVo getProductCouponApplyData(PromotionRequestVo vo){
        log.info("----------PromotionService getProductCouponApplyData start");
        return (ProductCouponResponseVo) calculationFactory.getPromotionCalculationData(vo, PromotionType.productCoupon);
    }

    public CartCouponResponseVo getCartCouponApplyData(PromotionRequestVo vo){
        log.info("----------PromotionService getCartCouponApplyData start");
        return (CartCouponResponseVo) calculationFactory.getPromotionCalculationData(vo, PromotionType.cartCoupon);
    }
}
