package com.plateer.ec1.promotion.service;

import com.plateer.ec1.promotion.calculation.factory.CalculationFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CalculationFactory calculationFactory;

    public PriceDiscountResponseDto getPriceDiscountApplyData(PromotionRequestDto vo){
        log.info("----------PromotionService getPriceDiscountApplyData start");
        return (PriceDiscountResponseDto) calculationFactory.getPromotionCalculationData(vo, PromotionType.priceDiscount);
    }

    public ProductCouponResponseDto getProductCouponApplyData(PromotionRequestDto vo){
        log.info("----------PromotionService getProductCouponApplyData start");
        return (ProductCouponResponseDto) calculationFactory.getPromotionCalculationData(vo, PromotionType.productCoupon);
    }

    public CartCouponResponDto getCartCouponApplyData(PromotionRequestDto vo){
        log.info("----------PromotionService getCartCouponApplyData start");
        return (CartCouponResponDto) calculationFactory.getPromotionCalculationData(vo, PromotionType.cartCoupon);
    }
}
