package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class PriceDiscountResponseVo extends BaseResponseVo {
    private List<ProductVo> productList;
}
