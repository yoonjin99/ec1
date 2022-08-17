package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.validation.ClaimValidationMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.model.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExchangeDataCreatorImpl extends ClaimDataCreator {
    public ExchangeDataCreatorImpl(ClaimValidationMapper claimValidationMapper) {
        super(claimValidationMapper);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.EX;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("교환 update 데이터 생성 로직 호출");
        return null;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("교환 update 데이터 생성 로직 호출");
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
    public List<OpPayInfoModel> updatePayInfo(ClaimProcessVo vo) {
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

    @Override
    public List<OpPayInfoModel> insertPayInfo(ClaimProcessVo vo) {
        return null;
    }
}

// 교환은 ecoupon은 존재하지 않음.
