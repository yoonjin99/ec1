package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class PromotionRequestVo {
    private String mbrNo = "test01";
    private List<ProductVo> products;
}
