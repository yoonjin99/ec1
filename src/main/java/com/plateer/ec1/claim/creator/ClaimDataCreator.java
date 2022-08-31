package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class ClaimDataCreator implements ClaimDataCreatorInterface{

    private final ClaimMapper claimMapper;
    private final ClaimTrxMapper claimTrxMapper;

    private static final String CLAIM = "C";

    public abstract CreatorType getType();

    @Override
    public String getClaimNo(){
        return CLAIM + claimMapper.selectClaimNo();
    }

    @Override
    public ClaimProcessVo getClaimData(ClaimVo vo){
        if(vo.getClaimType().getType().equals(ClaimType.MCC.name())){
            return getEcouponCancelData(vo.getOrdNo());
        }
        return claimMapper.selectClaimProcess(vo);
    }

    @Override
    public ClaimProcessVo getEcouponCancelData(String ordNo){
        List<OpClmInfoModel> clm =  claimMapper.selectClaim(ordNo);
        ClaimProcessVo vo = new ClaimProcessVo();
        vo.setOpClmInfoModels(clm);
        return vo;
    }

    @Override
    public ClaimProcessVo getInsertClaimData(ClaimProcessVo claimProcessVo){
        log.info("클레임 등록할 데이터 가져오기------");
        return claimProcessVo.getClaimType().equals(ClaimType.MCC.name()) ? null : insertDataCreator(claimProcessVo);
    }

    @Override
    public ClaimProcessVo getUpdateClaimData(ClaimProcessVo claimProcessVo){
        log.info("클레임 업데이트할 데이터 가져오기------");
        return updateDataCreator(claimProcessVo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveClaimData(ClaimProcessVo insertData, ClaimProcessVo updateData){
        log.info("주문 클레임 데이터 저장");
        if(!Objects.isNull(insertData)){
            Optional.ofNullable(insertData.getOpClmInfoModels()).ifPresent(claimTrxMapper::insertOpClmInfo);
            Optional.ofNullable(insertData.getOpOrdBnfRelInfoModels()).ifPresent(claimTrxMapper::insertOpOrdBnfRelInfo);
            Optional.ofNullable(insertData.getOpOrdCostInfoModels()).ifPresent(claimTrxMapper::insertOpOrdCostInfo);
        }
        if(!Objects.isNull(updateData)){
            if(!updateData.getClaimType().equals(ClaimType.MCC.name())){
                Optional.ofNullable(updateData.getOpClmInfoModels()).ifPresent(claimTrxMapper::updateOpClmInfo);
            }else{
                Optional.ofNullable(updateData.getOpClmInfoModels()).ifPresent(claimTrxMapper::updateEcouponComplete);
            }
            Optional.ofNullable(updateData.getOpOrdBnfInfoModels()).ifPresent(claimTrxMapper::updateOpOrdBnfInfo);
        }
    }
}
