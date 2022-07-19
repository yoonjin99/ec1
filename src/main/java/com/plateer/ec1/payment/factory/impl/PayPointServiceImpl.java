package com.plateer.ec1.payment.factory.impl;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayPointServiceImpl implements PaymentTypeService {

//    private final Point point;

    @Override
    public void validateAuth(PayInfoVo payInfo) {}

    @Override
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo, PayInfoVo payInfo) {
        log.info("-----------------Point approvePay start");
//        point.usePoint(null, 0);
        return null;
    }

    @Override
    public void cancelPay(PaymentCancelRequestVo paymentCancelRequestVo) {
        log.info("-----------------Point cancelPay start");
//        point.cancelPoint(null, 0);
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {}

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
