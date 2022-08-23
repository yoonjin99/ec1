package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.*;
import lombok.Data;

import java.util.List;

@Data
public class ClaimProcessVo {
    private String clmNo;
    private String ordNo;
    private String claimType;
    private Long cnclPrice;

    private List<OpClmInfoModel> opClmInfoModels;
    private List<OpOrdCostInfoModel> opOrdCostInfoModels;
    private List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModels;
    private List<OpOrdBnfInfoModel> opOrdBnfInfoModels;
    private OpPayInfoModel opPayInfoModel;
}
