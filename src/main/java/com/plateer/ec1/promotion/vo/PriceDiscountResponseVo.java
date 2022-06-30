package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
public class PriceDiscountResponseVo extends BaseResponseVo {
    private List<ProductVo> productList;
}
