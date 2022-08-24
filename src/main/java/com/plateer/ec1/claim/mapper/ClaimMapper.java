package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimStatusVo;
import com.plateer.ec1.claim.vo.OrdClaimInfoVo;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimMapper {
    List<ClaimStatusVo> statusCheck(List<OrdClaimInfoVo> ordClaimInfoVoList);
    String selectClaimNo();
    ClaimProcessVo selectClaimProcess(String ordNo);
    List<OpClmInfoModel> selectClaim(String ordNo);
    boolean amountValid(String clmNo);
}
