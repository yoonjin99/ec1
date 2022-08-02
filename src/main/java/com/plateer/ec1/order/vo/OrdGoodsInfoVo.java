package com.plateer.ec1.order.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrdGoodsInfoVo {
    @NotEmpty
    private String ordGoodsNo;
    private String ordItemNo;
    private String goodsSellTpCd;
    @Min(0)
    private int ordCnt;
    private Long prmPrc;
    private Long salePrc;
    private List<OrdBnfInfoVo> ordBnfInfoVo;
}
