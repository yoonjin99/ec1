package com.plateer.ec1.payment.factory.impl;

import com.plateer.ec1.common.code.order.OPT0009Code;
import com.plateer.ec1.common.code.order.OPT0010Code;
import com.plateer.ec1.common.code.order.OPT0011Code;
import com.plateer.ec1.common.code.promotion.PRM0011Code;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.mapper.PaymentInicisMapper;
import com.plateer.ec1.payment.mapper.PaymentInicisTrxMapper;
import com.plateer.ec1.payment.vo.*;
import com.plateer.ec1.promotion.service.point.PointService;
import com.plateer.ec1.promotion.vo.PointRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayPointServiceImpl implements PaymentTypeService {

    private final PointService point;
    private final PaymentInicisTrxMapper inicisTrxMapper;
    private final PaymentInicisMapper inicisMapper;

    @Override
    public void validateAuth(PayInfoVo payInfo) {}

    @Override
    @Transactional
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo, PayInfoVo payInfo) {
        log.info("-----------------Point approvePay start");
        Long pntSeq = usePointCall(payInfo.getPrice());
        insertUsePoint(orderInfoVo, payInfo, pntSeq);
        return new ApproveResVo();
    }

    private Long usePointCall(Long price){
        PointRequestVo pointRequestVo = new PointRequestVo();
        pointRequestVo.setMemberNo("test01");
        pointRequestVo.setPointAmt(price);
        pointRequestVo.setSaveUseCcd(PRM0011Code.USE.getType());
        return point.usePoint(pointRequestVo);
    }

    private void insertUsePoint(OrderInfoVo orderInfoVo, PayInfoVo payInfo, Long pntSeq){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
                .payNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .ordNo(orderInfoVo.getOrdNo())
                .payCcd(OPT0010Code.PAY.getType())
                .payPrgsScd(OPT0011Code.SUCCESSPAY.getType())
                .payMnCd(OPT0009Code.POINT.getType())
                .payAmt(payInfo.getPrice())
                .rfndAvlAmt(payInfo.getPrice())
                .cnclAmt(0L)
                .trsnId(String.valueOf(pntSeq))
                .payCmtDtime(LocalDateTime.now())
                .build();
        inicisTrxMapper.insertPayinfo(opPayInfoModel);
    }

    @Override
    @Transactional
    public void cancelPay(PaymentCancelRequestVo paymentCancelRequestVo) {
        log.info("-----------------Point cancelPay start");
        CancelInfoVo info = inicisMapper.selectPayInfo(paymentCancelRequestVo);

        if(info.getOpPayInfoModel().getRfndAvlAmt() < paymentCancelRequestVo.getCancelPrice()){
            throw new IllegalStateException("환불 가능 금액보다 취소 금액이 더 크기 때문에 취소 불가능합니다.");
        }

        Long pntSeq = cancelPointCall(paymentCancelRequestVo.getCancelPrice());
        insertCancelPoint(paymentCancelRequestVo, pntSeq, info.getOpPayInfoModel().getPayNo());
    }

    private Long cancelPointCall(Long price){
        PointRequestVo pointRequestVo = new PointRequestVo();
        pointRequestVo.setMemberNo("test01");
        pointRequestVo.setPointAmt(price);
        pointRequestVo.setSaveUseCcd(PRM0011Code.ADD.getType());
        return point.cancelPoint(pointRequestVo);
    }

    private void insertCancelPoint(PaymentCancelRequestVo paymentCancelRequestVo, Long pntSeq, String orgPayNo){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
                .payNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .ordNo(paymentCancelRequestVo.getOrdNo())
                .payCcd(OPT0010Code.CANCEL.getType())
                .payPrgsScd(OPT0011Code.CANCEL.getType())
                .payMnCd(OPT0009Code.POINT.getType())
                .payAmt(paymentCancelRequestVo.getCancelPrice())
                .rfndAvlAmt(0L)
                .cnclAmt(0L)
                .clmNo(paymentCancelRequestVo.getClmNo())
                .orgPayNo(orgPayNo)
                .trsnId(String.valueOf(pntSeq))
                .payCmtDtime(LocalDateTime.now())
                .build();
        inicisTrxMapper.insertPayinfo(opPayInfoModel);
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {}

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
