package com.plateer.ec1.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.OrderInfoVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import com.plateer.ec1.payment.vo.PaymentCancelRequestVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointPaymentTest {

    @Autowired
    public PaymentService payService;

    @Test
    @DisplayName("포인트 결제 테스트")
    void pointUse() throws Exception {
        PayInfoVo info = new PayInfoVo();
        info.setNmInput("테스트");
        info.setBankCode("03");
        info.setPrice(10000);
        info.setPaymentType(PaymentType.POINT);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrdNo("O20220712134705");
        orderInfoVo.setBuyerEmail("dbswls1999@naver.com");
        orderInfoVo.setGoodName("라운드넥티셔츠");
        orderInfoVo.setBuyerName("테스트");
        payService.approve(orderInfoVo, info);
    }

    @Test
    @DisplayName("포인트 결제 취소 테스트")
    void pointCancel(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.POINT);
        vo.setCancelPrice(7000L);
        vo.setOrdNo("O20220712134705");
        vo.setClmNo("C5");
        payService.cancel(vo);
    }

    @Test
    @DisplayName("가지고 있는 포인트보다 더 큰 금액을 결제했다면?")
    void error1() throws JsonProcessingException {
        PayInfoVo info = new PayInfoVo();
        info.setNmInput("테스트");
        info.setBankCode("03");
        info.setPrice(100000000);
        info.setPaymentType(PaymentType.POINT);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrdNo("O20220712134705");
        orderInfoVo.setBuyerEmail("dbswls1999@naver.com");
        orderInfoVo.setGoodName("라운드넥티셔츠");
        orderInfoVo.setBuyerName("테스트");

        Assertions.assertThrows(IllegalStateException.class, () -> {
            payService.approve(orderInfoVo, info);
        });
    }

    @Test
    @DisplayName("환불 가능한 포인트 보다 더 큰 금액을 취소하려 한다면?")
    void error2(){
        PaymentCancelRequestVo vo = new PaymentCancelRequestVo();
        vo.setPaymentType(PaymentType.POINT);
        vo.setCancelPrice(150000L);
        vo.setOrdNo("O20220712134705");
        vo.setClmNo("C5");

        Assertions.assertThrows(IllegalStateException.class, () -> {
            payService.cancel(vo);
        });
    }
}
