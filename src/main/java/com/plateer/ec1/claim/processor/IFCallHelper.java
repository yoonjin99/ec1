package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.CancelReqVo;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import com.plateer.ec1.promotion.service.coupon.CouponUseCancelService;
import com.plateer.ec1.promotion.vo.CouponRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class IFCallHelper {

    private final PaymentService paymentService;
    private final CouponUseCancelService couponUseCancelService;

    public void callRestoreCouponIF(ClaimProcessVo orgData){
        log.info("----------IFCallHelper 쿠폰복원 실행--------");
        Map<String, Long> couponMap = couponPrice(orgData);
        for(OpOrdBnfInfoModel bnf : orgData.getOpOrdBnfInfoModels()){
            if(couponMap.containsKey(bnf.getOrdBnfNo()) && Objects.equals(couponMap.get(bnf.getOrdBnfNo()), bnf.getOrdBnfAmt())){
                couponUseCancelService.cancelCoupon(new CouponRequestVo().createRequest(bnf));
            }
        }
    }

    private Map<String , Long> couponPrice(ClaimProcessVo orgData){
        Map<String , Long> couponMap = new HashMap<>();
        for(OpOrdBnfInfoModel bnf : orgData.getOpOrdBnfInfoModels()){
            Long bnfPrice = 0L;
            for(OpOrdBnfRelInfoModel bnfRelInfoModel : orgData.getOpOrdBnfRelInfoModels()){
                if(bnf.getOrdBnfNo().equals(bnfRelInfoModel.getOrdBnfNo())){
                    bnfPrice += bnfRelInfoModel.getAplyAmt();
                }
            }
            couponMap.put(bnf.getOrdBnfNo(), bnfPrice);
        }

        return couponMap;
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
