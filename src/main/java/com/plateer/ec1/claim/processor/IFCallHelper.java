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
            couponUseCancelService.cancelCoupon(new CouponRequestVo().createRequest(bnf));
        }
    }

    public void callPaymentIF(ClaimProcessVo claimProcessVo){
        log.info("----------IFCallHelper 결제취소 실행--------");
        paymentService.cancel(new PaymentCancelRequestVo().createRequestVo(claimProcessVo));
    }

    public void callCreatePaymentIF(ClaimProcessVo claimProcessVo){
        log.info("----------IFCallHelper 결제취소 전 데이터 insert 실행--------");
        paymentService.createCancelData(new PaymentCancelRequestVo().createRequestVo(claimProcessVo));
    }
}
