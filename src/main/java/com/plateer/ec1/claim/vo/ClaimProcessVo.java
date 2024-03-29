package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ClaimProcessVo {
    private String clmNo;
    private String ordNo;
    private String claimType;
    private Long cnclPrice;
    private String imtnRsnCcd;

    private List<OpClmInfoModel> opClmInfoModels;
    private List<OpOrdCostInfoModel> opOrdCostInfoModels;
    private List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModels;
    private List<OpOrdBnfInfoModel> opOrdBnfInfoModels;
    private OpPayInfoModel opPayInfoModel;

    public ClaimProcessVo createVo(ClaimVo claimDto, ClaimProcessVo vo){
        vo.setClaimType(claimDto.getClaimType().name());
        vo.setClmNo(claimDto.getClaimNo());
        vo.setCnclPrice(claimDto.getCnclPrice());
        vo.setImtnRsnCcd(claimDto.getImtnRsnCcd());
        return vo;
    }

}