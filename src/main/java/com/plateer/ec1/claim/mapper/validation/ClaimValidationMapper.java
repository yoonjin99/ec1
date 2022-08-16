package com.plateer.ec1.claim.mapper.validation;

import com.plateer.ec1.claim.vo.ClaimStatusVo;
import com.plateer.ec1.claim.vo.OrdClaimInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimValidationMapper {
    List<ClaimStatusVo> statusCheck(List<OrdClaimInfoVo> ordClaimInfoVoList);
}
