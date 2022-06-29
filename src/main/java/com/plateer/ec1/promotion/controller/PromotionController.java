package com.plateer.ec1.promotion.controller;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.service.coupon.DownloadCouponService;
import com.plateer.ec1.promotion.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionService promotionService;
    private final DownloadCouponService downloadCouponService;

    // 쿠폰 다운로드
    @GetMapping("/coupon/download")
    public List<PromotionVo> downloadCoupon(CouponRequestVo vo){
        return downloadCouponService.downloadCoupon(vo);
    }

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
