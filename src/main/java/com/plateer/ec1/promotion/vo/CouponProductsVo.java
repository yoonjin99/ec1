package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class CouponProductsVo {
    private PromotionVo promotion;
    private List<ProductVo> productList;
}
