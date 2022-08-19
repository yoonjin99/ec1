package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.model.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class ClaimDataCreator {

    private final ClaimMapper claimMapper;
    private final ClaimTrxMapper claimTrxMapper;


    public abstract CreatorType getType();

    public String getClaimNo(){
        return claimMapper.selectClaimNo();
    }

    public ClaimProcessVo getClaimData(String ordNo){
        return claimMapper.selectClaimProcess(ordNo);
    }

    public ClaimProcessVo getInsertClaimData(ClaimProcessVo claimProcessVo){
        log.info("클레임 등록할 데이터 가져오기------");
        return insertDataCreator(claimProcessVo);
    }

    public ClaimProcessVo getUpdateClaimData(ClaimProcessVo claimProcessVo){
        log.info("클레임 업데이트할 데이터 가져오기------");
        return updateDataCreator(claimProcessVo);
    }

    public void saveClaimData(ClaimProcessVo insertData, ClaimProcessVo updateData){
        log.info("주문 클레임 데이터 저장");
        claimTrxMapper.insertOpClmInfo(insertData.getOpClmInfoModels());
        claimTrxMapper.insertOpOrdBnfRelInfo(insertData.getOpOrdBnfRelInfoModels());
        claimTrxMapper.insertOpOrdCostInfo(insertData.getOpOrdCostInfoModels());
        claimTrxMapper.updateOpClmInfo(updateData.getOpClmInfoModels());
        claimTrxMapper.updateOpOrdBnfInfo(updateData.getOpOrdBnfInfoModels());
    }

    public abstract ClaimProcessVo updateDataCreator(ClaimProcessVo vo);
    public abstract ClaimProcessVo insertDataCreator(ClaimProcessVo vo);

    public abstract List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo);

    public abstract List<OpOrdCostInfoModel> updateOrderCost(ClaimProcessVo vo);

    public abstract List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo);

    public abstract List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo);

    public abstract List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo);

    public abstract List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo);
}
