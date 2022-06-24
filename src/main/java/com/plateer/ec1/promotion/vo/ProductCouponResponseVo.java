package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductCouponResponseVo extends BaseResponseVo {
    private List<ProductCouponsVo> productPromotionList;
}
