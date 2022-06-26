package com.plateer.ec1.claim.processor;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.CancelReqVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
public class IFCallHelper {

    private final PaymentService paymentService;

    public void callRestoreCouponIF(){
        log.info("----------IFCallHelper 재고복원 실행--------");
    }

    public void callPaymentIF(){
        log.info("----------IFCallHelper 결제취소 실행--------");
        CancelReqVo cancelReqVO = new CancelReqVo();
        cancelReqVO.setPaymentType(PaymentType.INICIS);
        paymentService.cancel(cancelReqVO);
    }
}
