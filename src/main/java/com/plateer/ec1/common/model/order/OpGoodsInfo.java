package com.plateer.ec1.common.model.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpGoodsInfo {
    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private String goodsSellTpCd;
    private String goodsDlvTpCd;
    private String goodsNm;
    private String itemNm;
    private Long sellAmt;
    private Long sellDcAmt;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

}
