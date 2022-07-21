package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdBnfInfoVo {
    private String prmNo;
    private String cpnKndCd;
    private String cpnIssNo;
    private String degrCcd;
    private List<GoodsInfoVo> bnfApplyGoods;
}
