package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class CartCouponResponseVo extends BaseResponseVo {
    private List<CouponProductsVo> promotionProductList;
}
