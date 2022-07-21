package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdGoodsInfoVo {
    private String ordGoodsNo;
    private String ordItemNo;
    private String goodsSellTpCd;
    private int ordCnt;
    private List<OrdBnfInfoVo> ordBnfInfoVo;
}
