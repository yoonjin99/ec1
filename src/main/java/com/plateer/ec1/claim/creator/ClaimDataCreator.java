package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ClaimDataCreator {

    public abstract ClaimType getType();

    public String getClaimNo(ClaimVo claimDto){
        return "C1";
    }

//    public ClaimModel getInsertClaimData(){
//        log.info("클레임 등록할 데이터 가져오기------");
//        ClaimModel claimModel = new ClaimModel();
//        claimModel = insertOrderClaim(claimModel);
//        claimModel = insertOrderCost(claimModel);
//        claimModel = insertOrderBenefitRelation(claimModel);
//        return claimModel;
//    }
//
//    public ClaimModel getUpdateClaimData(){
//        log.info("클레임 업데이트할 데이터 가져오기------");
//        ClaimModel claimModel = new ClaimModel();
//        claimModel = updateOrderClaim(claimModel);
//        claimModel = updateOrderCost(claimModel);
//        claimModel = updateOrderBenefitData(claimModel);
//        return claimModel;
//    }
//
//    public void saveClaimData(ClaimModel insertData, ClaimModel updateData){
//        log.info("주문 클레임 데이터 저장");
//    }
//
//    public abstract ClaimModel updateOrderBenefitData(ClaimModel claimModel);
//
//    public abstract ClaimModel insertOrderBenefitRelation(ClaimModel claimModel);
//
//    public abstract ClaimModel updateOrderCost(ClaimModel claimModel);
//
//    public abstract ClaimModel insertOrderCost(ClaimModel claimModel);
//
//    public abstract ClaimModel updateOrderClaim(ClaimModel claimModel);
//
//    public abstract ClaimModel insertOrderClaim(ClaimModel claimModel);

}
