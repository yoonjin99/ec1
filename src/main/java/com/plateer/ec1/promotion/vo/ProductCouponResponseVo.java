package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
public class ProductCouponResponseVo extends BaseResponseVo {
    private List<ProductCouponsVo> productPromotionList;
}
