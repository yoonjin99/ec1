package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.factory.PaymentTypeServiceFactory;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentTypeServiceFactory paymentServiceFactory;

    public ApproveResVo approve(PayInfoVo payInfo){
        log.info("---------------PayService approve start---------------------");
        PaymentTypeService paymentTypeService = paymentServiceFactory.getPaymentService(payInfo.getPaymentType());
        return paymentTypeService.approvePay(payInfo);
    }

    public void cancel(CancelReqVo cancelReqVO){
        log.info("---------------PayService cancel start---------------------");
        PaymentTypeService paymentTypeService = paymentServiceFactory.getPaymentService(cancelReqVO.getPaymentType());
        OriginalOrderVo originalOrder = new OriginalOrderVo();
        paymentTypeService.cancelPay(originalOrder);
    }

    public void netCancel(NetCancelReqVo netCancelReqVO){
        log.info("---------------PayService netCancel start---------------------");
    }
}
