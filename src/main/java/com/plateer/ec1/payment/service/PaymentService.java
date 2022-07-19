package com.plateer.ec1.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.factory.PaymentTypeServiceFactory;
import com.plateer.ec1.payment.mapper.PaymentInicisTrxMapper;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class PaymentService {
    private final PaymentTypeServiceFactory paymentServiceFactory;
    private final PaymentInicisTrxMapper inicisTrxMapper;

    public ApproveResVo approve(@Valid OrderInfoVo orderInfoVo,@Valid PayInfoVo payInfo) throws JsonProcessingException {
        log.info("---------------PayService approve start---------------------");
        PaymentTypeService paymentTypeService = paymentServiceFactory.getPaymentService(payInfo.getPaymentType());
        return paymentTypeService.approvePay(orderInfoVo, payInfo);
    }

    public void createCancelData(PaymentCancelRequestVo cancelRequestVo){
        log.info("---------------취소 api 호출하기 전에 금액 검증을 위해 미리 데이터 update 해놓음---------------------");
        inicisTrxMapper.updateCancelResult(cancelRequestVo);
    }

    public void cancel(PaymentCancelRequestVo cancelRequestVo){
        log.info("---------------PayService cancel start---------------------");
        PaymentTypeService paymentTypeService = paymentServiceFactory.getPaymentService(cancelRequestVo.getPaymentType());
        paymentTypeService.cancelPay(cancelRequestVo);
    }

    public void netCancel(NetCancelReqVo netCancelReqVO){
        log.info("---------------PayService netCancel start---------------------");
    }
}
