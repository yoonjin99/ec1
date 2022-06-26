package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ClaimDataCreator {

    public abstract ClaimType getType();

    public String getClaimNo(ClaimVo claimDto){
        return "C1";
    }

    public ClaimProcessVo getInsertClaimData(){
        log.info("클레임 등록할 데이터 가져오기------");
        ClaimProcessVo claimProcessVo = new ClaimProcessVo();
        claimProcessVo = insertOrderClaim(claimProcessVo);
        claimProcessVo = insertOrderCost(claimProcessVo);
        claimProcessVo = insertOrderBenefitRelation(claimProcessVo);
        return claimProcessVo;
    }

    public ClaimProcessVo getUpdateClaimData(){
        log.info("클레임 업데이트할 데이터 가져오기------");
        ClaimProcessVo claimProcessVo = new ClaimProcessVo();
        claimProcessVo = updateOrderClaim(claimProcessVo);
        claimProcessVo = updateOrderCost(claimProcessVo);
        claimProcessVo = updateOrderBenefitData(claimProcessVo);
        return claimProcessVo;
    }

    public void saveClaimData(ClaimProcessVo insertData, ClaimProcessVo updateData){
        log.info("주문 클레임 데이터 저장");
    }

    public abstract ClaimProcessVo updateOrderBenefitData(ClaimProcessVo claimModel);

    public abstract ClaimProcessVo insertOrderBenefitRelation(ClaimProcessVo claimModel);

    public abstract ClaimProcessVo updateOrderCost(ClaimProcessVo claimModel);

    public abstract ClaimProcessVo insertOrderCost(ClaimProcessVo claimModel);

    public abstract ClaimProcessVo updateOrderClaim(ClaimProcessVo claimModel);

    public abstract ClaimProcessVo insertOrderClaim(ClaimProcessVo claimModel);

}
