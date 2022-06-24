package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentServiceFactory;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentServiceFactory paymentServiceFactory;

    public ApproveResVo approve(PayInfoVo payInfo){
        log.info("---------------PayService approve start---------------------");
//        PaymentService paymentService = paymentServiceFactory.getPaymentService(payInfo.getPaymentType());
//        return paymentService.approvePay(payInfo);
        return null;
    }

    public void cancel(CancelReqVo cancelReqVO){
        log.info("---------------PayService cancel start---------------------");
//        PaymentService paymentService = paymentServiceFactory.getPaymentService(cancelReqVO.getPaymentType());
        OriginalOrderVo originalOrder = new OriginalOrderVo();
//        paymentService.cancelPay(originalOrder);
    }

    public void netCancel(NetCancelReqVo netCancelReqVO){
        log.info("---------------PayService netCancel start---------------------");
    }
}
