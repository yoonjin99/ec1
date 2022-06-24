package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.ApproveResVo;
import com.plateer.ec1.payment.vo.NetCancelReqVo;
import com.plateer.ec1.payment.vo.OriginalOrderVo;
import com.plateer.ec1.payment.vo.PayInfoVo;

public interface PaymentService {
    public void validateAuth(PayInfoVo payInfo);
    public ApproveResVo approvePay(PayInfoVo payInfo);
    public void cancelPay(OriginalOrderVo originalOrder);
    public void netCancel(NetCancelReqVo netCancelReqVO);
    PaymentType getType();
}
