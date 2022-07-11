package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.*;

public interface PaymentTypeService {
    public void validateAuth(PayInfoVo payInfo);
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo,PayInfoVo payInfo);
    public void cancelPay(OriginalOrderVo originalOrder);
    public void netCancel(NetCancelReqVo netCancelReqVO);
    PaymentType getType();
}
