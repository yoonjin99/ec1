package com.plateer.ec1.order.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrdCostInfo {
    private String aplyCcd;
    private String dvAmtTpCd;
    private String dvPlcTpCd;
    private Long orgDvAmt;
    private Long dvBnfAmt;
    @NotNull
    private Long aplyDvAmt;
}
