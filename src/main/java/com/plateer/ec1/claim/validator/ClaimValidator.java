package com.plateer.ec1.claim.validator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.ClaimStatusVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.code.order.OPT0004Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClaimValidator {

    private final ClaimMapper claimValidationMapper;

    public void isValidStatus(ClaimVo claimDto) {

        List<ClaimStatusVo> statusVo = claimValidationMapper.statusCheck(claimDto.getOrdClaimInfoVoList());

        boolean result = true;
        if(claimDto.getClaimType().getType().equals(CreatorType.C.getType())){
            result = statusVo.stream().allMatch(vo -> vo.getOrdPrgsScd().equals(OPT0004Type.OS.getType()));
        }else if(claimDto.getClaimType().getType().equals(CreatorType.R.getType())
                || claimDto.getClaimType().getType().equals(CreatorType.EX.getType())){
            result = statusVo.stream().allMatch(vo -> vo.getOrdPrgsScd().equals(OPT0004Type.SS.getType()));
        }else if(claimDto.getClaimType().getType().equals(ClaimType.RW.getType())){
            result = statusVo.stream().allMatch(vo -> vo.getOrdPrgsScd().equals(OPT0004Type.RA.getType()));
        }

        try {
            if(!result){
                throw new Exception("유효성 검증에 실패하였습니다."); // todo : custom exception
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isValidAmount(String clmNo){
        return claimValidationMapper.amountValid(clmNo);
    }

}
