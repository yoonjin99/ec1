package com.plateer.ec1.claim.processor;

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
        claimValidator.verifyAmount(claimDto);
    }

    protected Long insertLog(String claimNo){
        return orderHistoryService.insertOrderHistory(new OrderRequestVo());
    }

    protected void updateLog(Long monitoringLog, String claimNo){
        orderHistoryService.updateOrderHistory(monitoringLog, new OrderVo());
    }

    public abstract void doProcess(ClaimVo claimDto);
}
