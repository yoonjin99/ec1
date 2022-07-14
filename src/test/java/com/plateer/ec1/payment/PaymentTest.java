package com.plateer.ec1.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.service.PaymentNoticeService;
import com.plateer.ec1.payment.vo.AccountResponseVo;
import com.plateer.ec1.payment.vo.OrderInfoVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Slf4j
public class PaymentTest {

    @Autowired
    public PaymentService payService;

    @Autowired
    public PaymentNoticeService paymentVaccountService;

    @Test
    @DisplayName("가상계좌 통합 테스트")
    void insertTest() throws JsonProcessingException {
        log.info("---------------inicis 결제 통합테스트---------------------");
        PayInfoVo info = new PayInfoVo();
        info.setNmInput("테스트");
        info.setBankCode("03");
        info.setPrice(1);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrdNo("O20220712134700");
        orderInfoVo.setBuyerEmail("dbswls1999@naver.com");
        orderInfoVo.setGoodName("라운드넥티셔츠");
        orderInfoVo.setBuyerName("테스트");
        info.setPaymentType(PaymentType.INICIS);
        payService.approve(orderInfoVo, info);
        log.info("---------------inicis 결제 통합테스트 종료---------------------");
    }

    @Test
    @DisplayName("가상계좌 유효성 검증 테스트")
    void inicisValidationTest() throws JsonProcessingException {
        log.info("validation test -------------------");
        Throwable exception = Assertions.assertThrows(Exception.class, () ->{
            PayInfoVo info = new PayInfoVo();
            info.setNmInput("배윤진");
            info.setBankCode("03");
            info.setPrice(1);
            OrderInfoVo orderInfoVo = new OrderInfoVo();
//            orderInfoVo.setOrdNo("O20220712134700");
            orderInfoVo.setBuyerEmail("dbswls1999@naver.com");
            orderInfoVo.setGoodName("라운드넥티셔츠");
            orderInfoVo.setBuyerName("배윤진");
            info.setPaymentType(PaymentType.INICIS);
            payService.approve(orderInfoVo, info);
        });
        Assertions.assertEquals("approve.orderInfoVo.ordNo: 주문번호가 null 입니다." , exception.getMessage());
        
        // 유효한 이메일인가
        Throwable email_exception = Assertions.assertThrows(Exception.class, () ->{
            PayInfoVo info = new PayInfoVo();
            info.setNmInput("배윤진");
            info.setBankCode("03");
            info.setPrice(1);
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setOrdNo("O20220712134700");
            orderInfoVo.setBuyerEmail("dbswls1999@erekewrtnrt.");
            orderInfoVo.setGoodName("라운드넥티셔츠");
            orderInfoVo.setBuyerName("배윤진");
            info.setPaymentType(PaymentType.INICIS);
            payService.approve(orderInfoVo, info);
        });
        Assertions.assertEquals("approve.orderInfoVo.buyerEmail: 유효하지 않은 이메일 형식입니다." , email_exception.getMessage());
    }

    @Test
    @DisplayName("가상계좌 api 호출 테스트")
    <T>
    void inicisApiCallTest() throws JsonProcessingException {
        log.info("api call test --------------------");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("type","Pay");
        body.add("paymethod","Vacct");
        body.add("timestamp", "20220712000000");
        body.add("clientIp", "127.0.0.1");
        body.add("mid", "INIpayTest");
        body.add("moid", "test");
        body.add("goodName", "상품명");
        body.add("buyerName", "배윤진");
        body.add("buyerEmail", "dbswls1999@plateer.com");
        body.add("price", "1000");
        body.add("bankCode", "03");
        body.add("dtInput", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDD")));
        body.add("tmInput", LocalDateTime.now().format(DateTimeFormatter.ofPattern("hhmm")));
        body.add("nmInput", "배윤진");
        body.add("hashData", "b9ca9902b1750b5c5d05225b40a5eef5e15b9570768d7c4e172db1e57b7615bef0fcfb744fa0245b9e1c6bb82aff2fb2900a1009f505e5ee50058849755bcebe");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForEntity("https://iniapi.inicis.com/api/v1/formpay", new HttpEntity<>(body, httpHeaders), String.class).getBody();


        ObjectMapper objectMapper = new ObjectMapper();
        AccountResponseVo vo = objectMapper.readValue(response, AccountResponseVo.class);

        Assertions.assertEquals("01", vo.getResultCode());
    }

    @Test
    void vaccountDepositTest(){
        MultiValueMap<String, String> test = new LinkedMultiValueMap<>();
        test.add("len", "0534");
        test.add("no_tid", "ININPGVBNKINIpayTest20220714111313693185");
        test.add("dt_trans", "20220713");
        test.add("tm_trans", "150531");
        test.add("amt_input", "1");
        test.add("no_req_tid", "INIAPIVBNKINIpayTest20220713150505264190");
        paymentVaccountService.INIPayNotice(test);
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
