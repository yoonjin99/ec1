package com.plateer.ec1.order;

import com.plateer.ec1.order.service.OrderService;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class OrderControllerTest {

    @Autowired
    OrderService orderService;

    @Test
    void orderFOGeneralInicis() {
        log.info("--------------------FO일반 이니시스 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("FO");
//        orderRequest.setOrderType("general");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("inicis");
        orderService.order(orderRequest);
        log.info("--------------------FO일반 이니시스 결제 주문 종료---------------------");
    }

    @Test
    void orderBOGeneralInicis() {
        log.info("--------------------BO일반 이니시스 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("BO");
//        orderRequest.setOrderType("general");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("inicis");
        orderService.order(orderRequest);
        log.info("--------------------BO일반 이니시스 결제 주문 종료---------------------");
    }

    @Test
    void orderFOGeneralPoint() {
        log.info("--------------------FO일반 포인트 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("FO");
//        orderRequest.setOrderType("general");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("point");
        orderService.order(orderRequest);
        log.info("--------------------FO일반 포인트 결제 주문 종료---------------------");
    }

    @Test
    void orderBOGeneralPoint() {
        log.info("--------------------BO일반 포인트 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("BO");
//        orderRequest.setOrderType("general");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("point");
        orderService.order(orderRequest);
        log.info("--------------------BO일반 포인트 결제 주문 종료---------------------");
    }

    @Test
    void orderFOCouponInicis() {
        log.info("--------------------FO쿠폰 이니시스 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("FO");
//        orderRequest.setOrderType("ecoupon");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("inicis");
        orderService.order(orderRequest);
        log.info("--------------------FO쿠폰 이니시스 결제 주문 종료---------------------");
    }

    @Test
    void orderBOCouponInicis() {
        log.info("--------------------BO쿠폰 이니시스 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("BO");
//        orderRequest.setOrderType("ecoupon");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("inicis");
        orderService.order(orderRequest);
        log.info("--------------------BO쿠폰 이니시스 결제 주문 종료---------------------");
    }

    @Test
    void orderFOCouponPoint() {
        log.info("--------------------FO쿠폰 포인트 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("FO");
//        orderRequest.setOrderType("ecoupon");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("point");
        orderService.order(orderRequest);
        log.info("--------------------FO쿠폰 포인트 결제 주문 종료---------------------");
    }

    @Test
    void orderBOCouponPoint() {
        log.info("--------------------BO쿠폰 포인트 결제 주문 실행---------------------");
        OrderRequestVo orderRequest = new OrderRequestVo();
//        orderRequest.setSystemType("BO");
//        orderRequest.setOrderType("ecoupon");
//        orderRequest.setOrderNo("O1");
//        orderRequest.setPaymentType("point");
        orderService.order(orderRequest);
        log.info("--------------------BO쿠폰 포인트 결제 주문 종료---------------------");
    }

}