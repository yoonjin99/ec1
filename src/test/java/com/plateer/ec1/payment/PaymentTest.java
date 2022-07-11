package com.plateer.ec1.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.OrderInfoVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PaymentTest {

    @Autowired
    public PaymentService payService;

    @Test
    void paymentInicis() throws JsonProcessingException {
        log.info("---------------inicis 결제 실행---------------------");
        PayInfoVo info = new PayInfoVo();
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        info.setPaymentType(PaymentType.INICIS);
        payService.approve(orderInfoVo, info);
        log.info("---------------inicis 결제 종료---------------------");
    }

    @Test
    void paymentPoint(){
        log.info("---------------point 결제 실행---------------------");
        PayInfoVo info = new PayInfoVo();
        info.setPaymentType(PaymentType.POINT);
//        payService.approve(info);
        log.info("---------------point 결제 종료---------------------");
    }
}
