package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.CancelReqVo;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import com.plateer.ec1.promotion.service.coupon.CouponUseCancelService;
import com.plateer.ec1.promotion.vo.CouponRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
public class IFCallHelper {

    private final PaymentService paymentService;
    private final CouponUseCancelService couponUseCancelService;

    public void callRestoreCouponIF(ClaimProcessVo orgData){
        log.info("----------IFCallHelper 쿠폰복원 실행--------");
        for(OpOrdBnfInfoModel bnf : orgData.getOpOrdBnfInfoModels()){
            CouponRequestVo couponRequestVo = new CouponRequestVo();
            couponRequestVo.setCpnIssNo(bnf.getCpnIssNo());
            couponRequestVo.setPrmNo(bnf.getPrmNo());
            couponRequestVo.setMbrNo("test01");
            couponUseCancelService.cancelCoupon(couponRequestVo);
        }
    }

    public void callPaymentIF(ClaimProcessVo claimProcessVo){
        log.info("----------IFCallHelper 결제취소 실행--------");
        PaymentCancelRequestVo cancelRequestVo = new PaymentCancelRequestVo();
        cancelRequestVo.setPaymentType(claimProcessVo.getOpPayInfoModel().getPayCcd().equals(PaymentType.INICIS.getType()) ? PaymentType.INICIS : PaymentType.POINT);
        cancelRequestVo.setOrdNo(claimProcessVo.getOrdNo());
        cancelRequestVo.setClmNo(claimProcessVo.getClmNo());
        cancelRequestVo.setCancelPrice(claimProcessVo.getCnclPrice());
        paymentService.cancel(cancelRequestVo);
    }

    public void callCreatePaymentIF(ClaimProcessVo claimProcessVo){
        PaymentCancelRequestVo cancelRequestVo = new PaymentCancelRequestVo();
        cancelRequestVo.setPaymentType(claimProcessVo.getOpPayInfoModel().getPayCcd().equals(PaymentType.INICIS.getType()) ? PaymentType.INICIS : PaymentType.POINT);
        cancelRequestVo.setOrdNo(claimProcessVo.getOrdNo());
        cancelRequestVo.setClmNo(claimProcessVo.getClmNo());
        cancelRequestVo.setCancelPrice(claimProcessVo.getCnclPrice());
        paymentService.createCancelData(cancelRequestVo);
    }
}
