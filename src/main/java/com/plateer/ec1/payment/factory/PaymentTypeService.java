package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.*;

public interface PaymentTypeService {
    void validateAuth(PayInfoVo payInfo);
    ApproveResVo approvePay(OrderInfoVo orderInfoVo,PayInfoVo payInfo);
    void cancelPay(PaymentCancelRequestVo paymentCancelRequestVo);
    void netCancel(NetCancelReqVo netCancelReqVO);
    PaymentType getType();
}
