package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.order.service.OrderHistoryService;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class ClaimProcessor {
    public abstract ProcessorType getType();

    protected final ClaimValidator claimValidator;
    protected final OrderHistoryService orderHistoryService;

    protected void doValidationProcess(ClaimVo claimDto){
        log.info("-------------- ClaimProcessor 유효성 검사 실행 --------------------");
        claimValidator.isValidStatus(claimDto);
    }

    protected void amountValid(String clmNo) throws Exception {
        log.info("금액검증 실행");
        if(!claimValidator.isValidAmount(clmNo)){
            throw new Exception("금액 검증 실패");
        }
    }

    protected Long insertLog(ClaimVo claimVo){
        return orderHistoryService.insertOrderHistory(claimVo, "" ,claimVo.getClaimNo());
    }

    protected void updateLog(Long monitoringLog, ClaimVo vo){
        orderHistoryService.updateOrderHistory(monitoringLog, vo);
    }

    public abstract void doProcess(ClaimVo claimDto);
}
