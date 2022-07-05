package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductVo {
    @NotNull
    private String goodsNo;
    @NotNull
    private String itemNo;
    @NotNull
    private int prdPrice;
    private Long prmNo;
    private Long cpnIssNo;
}
