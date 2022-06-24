package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.promotion.vo.PromotionVo;

public class DownloadCouponService {
    private PromotionVo promotion;
    private String memberNo;

    private boolean checkAvailableDownloadCoupon(String memberNo, PromotionVo promotion){
        return true;
    }

    public PromotionVo downloadCoupon(String memberNo, PromotionVo promotion){
        return null;
    }
}
