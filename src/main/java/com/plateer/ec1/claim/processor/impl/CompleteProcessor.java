package com.plateer.ec1.claim.processor.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.DataCreatorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.processor.IFCallHelper;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CompleteProcessor extends ClaimProcessor {

    private final IFCallHelper ifCallHelper;
    private final DataCreatorFactory dataCreatorFactory;

    public CompleteProcessor(ClaimValidator claimValidator, IFCallHelper ifCallHelper, DataCreatorFactory dataCreatorFactory) {
        super(claimValidator);
        this.ifCallHelper = ifCallHelper;
        this.dataCreatorFactory = dataCreatorFactory;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.COMPLETE;
    }

    @Override
    @EventListener
    public void doProcess(ClaimVo claimDto) {
        log.info("----------CompleteProcessor doProcess 실행--------");
        Long monitoringLog = null;
        String claimNo = "";
        try {
            // 데이터 생성
//            ClaimDataCreator claimDataCreator = dataCreatorFactory.getClaimDataCreator(claimDto.getClaimType());
            // 클레임 번호 채번
//            claimNo = claimDataCreator.getClaimNo(claimDto);
            // 주문 모니터링 로그 등록
//            monitoringLog = insertLog(claimNo);
            // 유효성 검증
//            doValidationProcess(claimDto);
            // update 대상 데이터 생성
//            ClaimModel updateData = claimDataCreator.getUpdateClaimData();
            // 데이터 저장
//            claimDataCreator.saveClaimData(null, updateData);
            // 결제 IF 호출
//            ClaimModel claimModel = new ClaimModel();
//            claimModel.setPaymentType("inicis");
//            ifCallHelper.callPaymentIF(claimModel);
        }catch (Exception e){
            log.error( "error : " + e);
        }finally {
            // 주문 모니터링 update
//            updateLog(monitoringLog, claimNo);
        }
    }

}
