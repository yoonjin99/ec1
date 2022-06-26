package com.plateer.ec1.payment.factory.impl;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.vo.ApproveResVo;
import com.plateer.ec1.payment.vo.NetCancelReqVo;
import com.plateer.ec1.payment.vo.OriginalOrderVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InicisServiceImpl implements PaymentTypeService {

    @Override
    public void validateAuth(PayInfoVo payInfo) {
        log.info("-----------------Inicis validateAuth start");
    }

    @Override
    public ApproveResVo approvePay(PayInfoVo payInfo) {
        log.info("-----------------Inicis approvePay start");
        return null;
    }

    @Override
    public void cancelPay(OriginalOrderVo originalOrder) {
        log.info("-----------------Inicis cancelPay start");
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {
        log.info("-----------------Inicis netCancel start");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
