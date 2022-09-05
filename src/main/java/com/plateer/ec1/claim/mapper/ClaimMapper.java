package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimStatusVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.claim.vo.OrdClaimInfoVo;
import com.plateer.ec1.common.model.order.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimMapper {
    List<ClaimStatusVo> statusCheck(List<OrdClaimInfoVo> ordClaimInfoVoList);
    String selectClaimNo();
    List<OpClmInfoModel> selectClaimInfo(ClaimVo vo);
    List<OpClmInfoModel> selectReturnClaimInfo(ClaimVo vo);
    List<OpOrdCostInfoModel> selectCostInfo(List<OpClmInfoModel> clmInfoModels);
    List<OpOrdBnfInfoModel> selectBnfInfo(List<OpClmInfoModel> clmInfoModels);
    List<OpOrdBnfRelInfoModel> selectBnfRelInfo(List<OpClmInfoModel> clmInfoModels);
    OpPayInfoModel selectPayInfo(List<OpClmInfoModel> clmInfoModels);
    List<OpClmInfoModel> selectClaim(String ordNo);
    boolean amountValid(String clmNo);
    Integer selectDvpGrpNo(String ordNo);
}
