package com.plateer.ec1.claim.processor.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.DataCreatorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.order.service.OrderHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AcceptWithdrawalProcessor extends ClaimProcessor {

    private final DataCreatorFactory dataCreatorFactory;

    public AcceptWithdrawalProcessor(ClaimValidator claimValidator, OrderHistoryService orderHistoryService, DataCreatorFactory dataCreatorFactory) {
        super(claimValidator,orderHistoryService);
        this.dataCreatorFactory = dataCreatorFactory;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.ACCEPTWITHDRWAL;
    }

    @Override
    public void doProcess(ClaimVo claimDto) {
        log.info("----------AcceptWithdrawalProcessor doProcess 실행--------");
        Long monitoringLog = null;
        String claimNo = "";
        try {
            // 데이터 생성
            ClaimDataCreator claimDataCreator = dataCreatorFactory.getClaimDataCreator(claimDto.getClaimType().getCreatorType());
            // 클레임 번호 채번
            claimNo = claimDataCreator.getClaimNo(claimDto);
            // 주문 모니터링 로그 등록
            monitoringLog = insertLog(claimNo);
            // 유효성 검증
            doValidationProcess(claimDto);

            ClaimProcessVo vo = claimDataCreator.getClaimData(); // 원주문 데이터 select
            ClaimProcessVo insertData = claimDataCreator.getInsertClaimData(vo);
            // update 대상 데이터 생성
            ClaimProcessVo updateData = claimDataCreator.getUpdateClaimData(vo);
            // 데이터 저장
            claimDataCreator.saveClaimData(insertData, updateData);
        }catch (Exception e){
            log.error("error : {}", e);
        }finally {
            // 주문 모니터링 update
            updateLog(monitoringLog, claimNo);
        }
    }
}
