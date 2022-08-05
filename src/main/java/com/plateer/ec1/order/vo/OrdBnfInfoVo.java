package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdBnfInfoVo {
    private Long prmNo;
    private String cpnKndCd;
    private Long cpnIssNo;
    private int degrCcd;
    private Long dcPrice;
    private List<GoodsInfoVo> bnfApplyGoods;
}
