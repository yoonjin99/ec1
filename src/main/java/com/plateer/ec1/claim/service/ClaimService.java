package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.factory.ProcessorFactory;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimService {

    private final ProcessorFactory processorFactory;

    public void claim(ClaimVo claimDto){
        log.info("-------------ClaimService claim method 실행--------------" + claimDto.getClaimType().name() + "----" + claimDto);
        processorFactory.getClaimProcessor(claimDto.getClaimType().getProcessor()).doProcess(claimDto);
    }
}
