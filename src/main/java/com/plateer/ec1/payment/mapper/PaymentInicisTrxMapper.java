package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.vo.ININoticeVo;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInicisTrxMapper {
    int insertPayinfo(OpPayInfoModel opPayInfoModel);
    int updateDepositResult(ININoticeVo iniNoticeVo);
    int updateCancelResult(PaymentCancelRequestVo cancelRequestVo);
}
