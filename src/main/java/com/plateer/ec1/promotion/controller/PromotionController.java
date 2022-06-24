package com.plateer.ec1.promotion.controller;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.vo.CartCouponResponDto;
import com.plateer.ec1.promotion.vo.PriceDiscountResponseDto;
import com.plateer.ec1.promotion.vo.ProductCouponResponseDto;
import com.plateer.ec1.promotion.vo.PromotionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    public PriceDiscountResponseDto getPriceDiscountApplyData(PromotionRequestDto requestPromotionVO){
        return promotionService.getPriceDiscountApplyData(requestPromotionVO);
    }

    public ProductCouponResponseDto getProductCouponApplyData(PromotionRequestDto requestPromotionVO){
        return promotionService.getProductCouponApplyData(requestPromotionVO);
    }

    public CartCouponResponDto getCartCouponApplyData(PromotionRequestDto requestPromotionVO){
        return promotionService.getCartCouponApplyData(requestPromotionVO);
    }
}
