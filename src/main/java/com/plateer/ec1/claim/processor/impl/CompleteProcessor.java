package com.plateer.ec1.claim.processor.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.DataCreatorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.processor.IFCallHelper;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.code.order.OPT0004Type;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.order.service.OrderHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CompleteProcessor extends ClaimProcessor {

    private final IFCallHelper ifCallHelper;
    private final DataCreatorFactory dataCreatorFactory;

    public CompleteProcessor(ClaimValidator claimValidator, OrderHistoryService orderHistoryService, IFCallHelper ifCallHelper, DataCreatorFactory dataCreatorFactory) {
        super(claimValidator,orderHistoryService);
        this.ifCallHelper = ifCallHelper;
        this.dataCreatorFactory = dataCreatorFactory;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.COMPLETE;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void doProcess(ClaimVo claimDto) {
        log.info("----------CompleteProcessor doProcess 실행--------");
        Long monitoringLog = null;
        try {
            ClaimDataCreator claimDataCreator = dataCreatorFactory.getClaimDataCreator(claimDto.getClaimType().getCreatorType());
            claimDto.setClaimNo(claimDataCreator.getClaimNo());

            monitoringLog = insertLog(claimDto);

            doValidationProcess(claimDto);

            ClaimProcessVo orgData = claimDataCreator.getClaimData(claimDto);
            orgData = orgData.createVo(claimDto, orgData);

            ClaimProcessVo insertData = claimDataCreator.getInsertClaimData(orgData);
            ClaimProcessVo updateData = claimDataCreator.getUpdateClaimData(orgData);

            claimDataCreator.saveClaimData(insertData, updateData);

            ifCallHelper.callCreatePaymentIF(orgData);

            amountValid(claimDto.getClaimNo());

            ifCallHelper.callRestoreCouponIF(orgData);

            ifCallHelper.callPaymentIF(orgData);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 주문 모니터링 update
            updateLog(monitoringLog, claimDto);
        }
    }
}
