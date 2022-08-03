package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdBnfInfoVo {
    private Long prmNo;
    private String cpnKndCd;
    private String cpnIssNo;
    private int degrCcd;
    private Long dcPrice;
    private List<GoodsInfoVo> bnfApplyGoods;
}
