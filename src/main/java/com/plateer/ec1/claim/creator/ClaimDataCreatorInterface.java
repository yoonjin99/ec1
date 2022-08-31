package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.common.model.order.OpOrdCostInfoModel;

import java.util.List;


public interface ClaimDataCreatorInterface {
    String getClaimNo();
    ClaimProcessVo getClaimData(ClaimVo vo);
    ClaimProcessVo getEcouponCancelData(String ordNo);
    ClaimProcessVo getInsertClaimData(ClaimProcessVo claimProcessVo);
    ClaimProcessVo getUpdateClaimData(ClaimProcessVo claimProcessVo);
    void saveClaimData(ClaimProcessVo insertData, ClaimProcessVo updateData);
    ClaimProcessVo updateDataCreator(ClaimProcessVo vo);
    ClaimProcessVo insertDataCreator(ClaimProcessVo vo);
    List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo);
    List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo);
    List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo);
}
