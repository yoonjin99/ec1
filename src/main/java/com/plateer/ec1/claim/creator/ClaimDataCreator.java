package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.common.model.order.OpOrdCostInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public abstract class ClaimDataCreator {

    public abstract CreatorType getType();

    public String getClaimNo(ClaimVo claimDto){
        return "C1";
    }

    public ClaimProcessVo getClaimData(){
        ClaimProcessVo claimProcessVo = new ClaimProcessVo();
        // 원주문 데이터 select
        return claimProcessVo;
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


//    public abstract List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo);
//
//    public abstract List<OpOrdCostInfoModel> updateOrderCost(ClaimProcessVo vo);
//
//    public abstract List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo);

//    public abstract List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo);
//
//    public abstract List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo);
//
//    public abstract List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo);

}
