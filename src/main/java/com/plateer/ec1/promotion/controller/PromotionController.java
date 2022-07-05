package com.plateer.ec1.promotion.controller;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.service.coupon.DownloadCouponService;
import com.plateer.ec1.promotion.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionService promotionService;
    private final DownloadCouponService downloadCouponService;

    // 쿠폰 다운로드
    @PostMapping("/coupon/download")
    public List<PromotionVo> downloadCoupon(CouponRequestVo vo){
        return downloadCouponService.downloadCoupon(vo);
    }

    @PostMapping("/product")
    public ProductCouponResponseVo getProductCouponApplyData(@RequestBody @Valid PromotionRequestVo requestPromotionVO){
        return promotionService.getProductCouponApplyData(requestPromotionVO);
    }

    @PostMapping("/cart")
    public CartCouponResponseVo getCartCouponApplyData(@RequestBody @Valid PromotionRequestVo requestPromotionVO){
        return promotionService.getCartCouponApplyData(requestPromotionVO);
    }
}
