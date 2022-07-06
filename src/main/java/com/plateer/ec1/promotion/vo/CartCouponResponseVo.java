package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
public class CartCouponResponseVo extends BaseResponseVo {
    private List<CouponProductsVo> promotionProductList;
}
