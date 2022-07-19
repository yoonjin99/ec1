package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.vo.CancelInfoVo;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInicisMapper {
    CancelInfoVo selectPayInfo(PaymentCancelRequestVo cancelRequestVo);
}
