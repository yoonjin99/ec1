package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimType;
import lombok.Data;

@Data
public class ClaimVo {
    private String claimNo;
    private ClaimType claimType;
}
