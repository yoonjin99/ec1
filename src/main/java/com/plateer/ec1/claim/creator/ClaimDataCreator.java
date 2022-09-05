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

        List<OpClmInfoModel> clmInfoModels = null;
        if(vo.getClaimType().getType().equals(ClaimType.RW.name())){
            clmInfoModels = getReturnWithdraw(vo);
        }else{
            clmInfoModels = claimMapper.selectClaimInfo(vo);
        }
        List<OpOrdCostInfoModel> opOrdCostInfoModels = claimMapper.selectCostInfo(clmInfoModels);
        List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModels = claimMapper.selectBnfRelInfo(clmInfoModels);
        List<OpOrdBnfInfoModel> opOrdBnfInfoModels = claimMapper.selectBnfInfo(clmInfoModels);
        OpPayInfoModel opPayInfoModel = claimMapper.selectPayInfo(clmInfoModels);

        return ClaimProcessVo.builder()
                .ordNo(vo.getOrdNo())
                .opClmInfoModels(clmInfoModels)
                .opOrdCostInfoModels(opOrdCostInfoModels)
                .opOrdBnfRelInfoModels(opOrdBnfRelInfoModels)
                .opOrdBnfInfoModels(opOrdBnfInfoModels)
                .opPayInfoModel(opPayInfoModel)
                .build();
    }

    private List<OpClmInfoModel> getReturnWithdraw(ClaimVo vo){
        return claimMapper.selectReturnClaimInfo(vo);
    }

    @Override
    public ClaimProcessVo getEcouponCancelData(String ordNo){
        List<OpClmInfoModel> clm =  claimMapper.selectClaim(ordNo);
        return ClaimProcessVo.builder()
                .opClmInfoModels(clm)
                .build();
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
            if (insertData.getOpClmInfoModels().size() > 0) claimTrxMapper.insertOpClmInfo(insertData.getOpClmInfoModels());
            if (insertData.getOpOrdBnfRelInfoModels().size() > 0) claimTrxMapper.insertOpOrdBnfRelInfo(insertData.getOpOrdBnfRelInfoModels());
            if (insertData.getOpOrdCostInfoModels().size() > 0) claimTrxMapper.insertOpOrdCostInfo(insertData.getOpOrdCostInfoModels());
        }
        if(!Objects.isNull(updateData)){
            if(updateData.getClaimType().equals(ClaimType.MCC.name())){
                if (updateData.getOpClmInfoModels().size() > 0) claimTrxMapper.updateEcouponComplete(updateData.getOpClmInfoModels());
            } else if (updateData.getClaimType().equals(ClaimType.RW.name())){
                if (updateData.getOpClmInfoModels().size() > 0) claimTrxMapper.updateReturnWithdrawOpClmInfo(updateData.getOpClmInfoModels());
            } else{
                if (updateData.getOpClmInfoModels().size() > 0) claimTrxMapper.updateOpClmInfo(updateData.getOpClmInfoModels());
            }
            if (updateData.getOpOrdBnfInfoModels().size() > 0) claimTrxMapper.updateOpOrdBnfInfo(updateData.getOpOrdBnfInfoModels());
        }
    }
}
