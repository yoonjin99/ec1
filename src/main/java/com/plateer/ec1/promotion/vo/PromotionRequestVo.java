package com.plateer.ec1.promotion.vo;

import lombok.Data;

import java.util.List;

@Data
public class PromotionRequestVo {
    private String memberNo;
    private List<ProductVo> productList;
    private List couponIssueNoList;
}
