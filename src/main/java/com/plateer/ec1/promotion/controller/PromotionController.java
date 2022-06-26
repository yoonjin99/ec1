package com.plateer.ec1.promotion.controller;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.vo.CartCouponResponseVo;
import com.plateer.ec1.promotion.vo.PriceDiscountResponseVo;
import com.plateer.ec1.promotion.vo.ProductCouponResponseVo;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    public PriceDiscountResponseVo getPriceDiscountApplyData(PromotionRequestVo requestPromotionVO){
        return promotionService.getPriceDiscountApplyData(requestPromotionVO);
    }

    public ProductCouponResponseVo getProductCouponApplyData(PromotionRequestVo requestPromotionVO){
        return promotionService.getProductCouponApplyData(requestPromotionVO);
    }

    public CartCouponResponseVo getCartCouponApplyData(PromotionRequestVo requestPromotionVO){
        return promotionService.getCartCouponApplyData(requestPromotionVO);
    }
}
