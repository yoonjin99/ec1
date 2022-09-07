package com.plateer.ec1.claim.processor.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.DataCreatorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.order.service.OrderHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class WithdrawalProcessor extends ClaimProcessor {

    private final DataCreatorFactory dataCreatorFactory;

    public WithdrawalProcessor(ClaimValidator claimValidator, OrderHistoryService orderHistoryService, DataCreatorFactory dataCreatorFactory) {
        super(claimValidator,orderHistoryService);
        this.dataCreatorFactory = dataCreatorFactory;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.WITHDRAWAL;
    }

    @Override
    @Transactional
    public void doProcess(ClaimVo claimDto) {
        log.info("----------AcceptProcessor doProcess 실행--------");
        Long monitoringLog = null;
        try {
            // 데이터 생성
            ClaimDataCreator claimDataCreator = dataCreatorFactory.getClaimDataCreator(claimDto.getClaimType().getCreatorType());
            // 클레임 번호 채번
            claimDto.setClaimNo(claimDataCreator.getClaimNo());
            // 주문 모니터링 로그 등록
            monitoringLog = insertLog(claimDto);
            // 유효성 검증
            doValidationProcess(claimDto);

            ClaimProcessVo orgData = claimDataCreator.getClaimData(claimDto); // 원주문 데이터 select
            orgData = orgData.createVo(claimDto, orgData);

            ClaimProcessVo insertData = claimDataCreator.getInsertClaimData(orgData);
            // update 대상 데이터 생성
            ClaimProcessVo updateData = claimDataCreator.getUpdateClaimData(orgData);

            amountValid(claimDto, insertData);

            // 데이터 저장
            claimDataCreator.saveClaimData(insertData, updateData);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 주문 모니터링 update
            updateLog(monitoringLog, claimDto);
        }
    }

    private void amountValid(ClaimVo claimDto, ClaimProcessVo processVo) throws Exception {
        log.info("철회 금액 검증");
//        (1) 원 결제 테이블의 환불가능금액 =
//        (2) 원 주문 결제 금액 - 반품 접수 환불 금액 + 반품 철회 금액
        Long refundPrice = processVo.getOpPayInfoModel().getRfndAvlAmt();
        Long ordPrice = processVo.getOpPayInfoModel().getPayAmt();
    }
}
