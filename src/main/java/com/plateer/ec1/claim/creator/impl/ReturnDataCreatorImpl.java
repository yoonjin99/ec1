package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.model.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReturnDataCreatorImpl extends ClaimDataCreator {

    public ReturnDataCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.R;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("반품 update 데이터 생성 로직 호출");
        return null;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("반품 insert 데이터 생성 로직 호출");
        return null;
    }

    @Override
    public List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo) {
        return null;
    }

    @Override
    public List<OpOrdCostInfoModel> updateOrderCost(ClaimProcessVo vo) {
        return null;
    }

    @Override
    public List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo) {
        return null;
    }

    @Override
    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo) {
        return null;
    }

    @Override
    public List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo) {
        return null;
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
        return null;
    }
}

// ecoupon과 일반주문이 데이터가 같음
