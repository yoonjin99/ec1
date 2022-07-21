package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdDvpInfo {
    private int dvGrpNo;
    private List<GoodsInfoVo> goodsInfo;
    private OrdCostInfo ordCostInfo;
}
