package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVo {
    private String goodsNo;
    private String itemNo;
    private int prdPrice;
    private Long prmNo;
    private Long cpnIssNo;
}
