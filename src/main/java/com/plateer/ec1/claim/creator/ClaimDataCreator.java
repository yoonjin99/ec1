package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.validation.ClaimValidationMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class ClaimDataCreator {

    private final ClaimValidationMapper claimValidationMapper;

    public abstract CreatorType getType();

    public String getClaimNo(){
        return claimValidationMapper.selectClaimNo();
    }

    public ClaimProcessVo getClaimData(String ordNo){
        // 원주문 데이터 select
        return claimValidationMapper.selectClaimProcess(ordNo);
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
    }

    public abstract ClaimProcessVo updateDataCreator(ClaimProcessVo vo);
    public abstract ClaimProcessVo insertDataCreator(ClaimProcessVo vo);

    public abstract List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo);

    public abstract List<OpOrdCostInfoModel> updateOrderCost(ClaimProcessVo vo);

    public abstract List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo);

    public abstract List<OpPayInfoModel> updatePayInfo(ClaimProcessVo vo);

    public abstract List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo);

    public abstract List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo);

    public abstract List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo);

    public abstract List<OpPayInfoModel> insertPayInfo(ClaimProcessVo vo);

    // insert table
    // 1. 클레임
    // 2. 주문비용
    // 3. 주문혜택관계
    // 4. 주문결제

    // update table
    // 1. 클레임
    // 2. 혜택
    // 3. 결제

}
