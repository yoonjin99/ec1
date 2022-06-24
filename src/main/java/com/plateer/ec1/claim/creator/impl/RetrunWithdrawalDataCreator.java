package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetrunWithdrawalDataCreator extends ClaimDataCreator {
    @Override
    public ClaimType getType() {
        return ClaimType.RW;
    }
//    @Override
//    public ClaimModel updateOrderBenefitData(ClaimModel claimModel) {
//        log.info("주문 혜택 업데이트 데이터 생성---------");
//        return null;
//    }
//
//    @Override
//    public ClaimModel insertOrderBenefitRelation(ClaimModel claimModel) {
//        log.info("주문 혜택 등록 데이터 생성---------");
//        return null;
//    }
//
//    @Override
//    public ClaimModel updateOrderCost(ClaimModel claimModel) {
//        log.info("주문 비용 업데이트 데이터 생성---------");
//        return null;
//    }
//
//    @Override
//    public ClaimModel insertOrderCost(ClaimModel claimModel) {
//        log.info("주문 비용 등록 데이터 생성---------");
//        return null;
//    }
//
//    @Override
//    public ClaimModel updateOrderClaim(ClaimModel claimModel) {
//        log.info("주문 클레임 업데이트 데이터 생성---------");
//        return null;
//    }
//
//    @Override
//    public ClaimModel insertOrderClaim(ClaimModel claimModel) {
//        log.info("주문 클레임 등록 데이터 생성---------");
//        return null;
//    }
}
