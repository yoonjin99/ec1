package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReturnAcceptDataCreator extends ClaimDataCreator {
    @Override
    public ClaimType getType() {
        return ClaimType.RA;
    }

    @Override
    public ClaimProcessVo updateOrderBenefitData(ClaimProcessVo claimModel) {
        return null;
    }

    @Override
    public ClaimProcessVo insertOrderBenefitRelation(ClaimProcessVo claimModel) {
        return null;
    }

    @Override
    public ClaimProcessVo updateOrderCost(ClaimProcessVo claimModel) {
        return null;
    }

    @Override
    public ClaimProcessVo insertOrderCost(ClaimProcessVo claimModel) {
        return null;
    }

    @Override
    public ClaimProcessVo updateOrderClaim(ClaimProcessVo claimModel) {
        return null;
    }

    @Override
    public ClaimProcessVo insertOrderClaim(ClaimProcessVo claimModel) {
        return null;
    }

}
