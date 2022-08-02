package com.plateer.ec1.common.model.product;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PrItemInfoModel {
    private String goodsNo;
    private String itemNo;
    private String itemNm;
    private LocalDateTime sysRegDtime;
    private LocalDateTime sysModDtime;
    private String sysRegrId;
    private String sysModrId;

}
