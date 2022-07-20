package com.plateer.ec1.payment;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CancelTest {

    @Autowired
    PaymentService paymentService;

    @Test
    @DisplayName("입금전 전액 취소 테스트")
    void beforeAllCancel(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.INICIS);
        vo.setCancelPrice(1L);
        vo.setOrdNo("O20220712134702");
        vo.setClmNo("C1");
        paymentService.cancel(vo);
    }

    @Test
    @DisplayName("입금전 부분 취소 테스트")
    void beforePartCancel(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.INICIS);
        vo.setCancelPrice(4500L);
        vo.setOrdNo("O20220712134703");
        vo.setClmNo("C1");
        paymentService.cancel(vo);
    }

    @Test
    @DisplayName("입금후 전체 취소 테스트")
    void allCancel(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.INICIS);
        vo.setCancelPrice(10000L);
        vo.setOrdNo("O20220712134700");
        vo.setClmNo("C1");
        paymentService.cancel(vo);
    }

    @Test
    @DisplayName("입금후 부분 취소 테스트")
    void partCancel(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.INICIS);
        vo.setCancelPrice(4500L);
        vo.setOrdNo("O20220712134701");
        vo.setClmNo("C1");
        paymentService.cancel(vo);
    }
}
