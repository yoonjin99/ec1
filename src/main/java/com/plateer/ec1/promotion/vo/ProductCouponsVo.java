package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductCouponsVo {
    private ProductVo product;
    private List<PromotionVo> promotionList;
}
