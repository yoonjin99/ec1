package com.plateer.ec1.order.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderProductViewVo {
    private String goodsNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;
    private String prgsStatCd;
    private String goodsDlvTpCd;
    private String goodsNo;
    private String itemNo;
    private String itemNm;
    private int ordCnt;
}
