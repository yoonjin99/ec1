package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimType;
import lombok.Data;

import java.util.List;

@Data
public class ClaimVo {
    private ClaimType claimType;
    private String claimNo;
    private String ordNo;
    private List<OrdClaimInfoVo> ordClaimInfoVoList;
}
