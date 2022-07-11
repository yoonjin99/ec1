package com.plateer.ec1.payment.factory.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class InicisServiceImpl implements PaymentTypeService {

    private static final String URL = "https://iniapi.inicis.com/api/v1/formpay";

    @Override
    public void validateAuth(PayInfoVo payInfo) {
        log.info("-----------------Inicis validateAuth start");
    }

    @Override
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo, PayInfoVo payInfo) {
        log.info("-----------------Inicis approvePay start");
        // TODO : 유효성 검증 + 테스트 코드 작성
        // approveResVO 에 어떤 값이 들어오는지 - > responseData ? -> 주문 데이터
        AccountVo accountVo = AccountVo.builder()
                .goodName(orderInfoVo.getGoodName())
                .buyerName(orderInfoVo.getBuyerName())
                .buyerEmail(orderInfoVo.getBuyerEmail())
                .price(payInfo.getPrice())
                .bankCode(payInfo.getBankCode())
                .nmInput(payInfo.getNmInput())
                .timestamp(LocalDateTime.now())
                .clientIp("127.0.0.1")
                .moid("INIpayTest_1657505411924")
                .dtInput(LocalDateTime.now().plusDays(1))
                .tmInput(LocalDateTime.now())
                .build();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, Object> requestMap = objectMapper.convertValue(accountVo, new TypeReference<Map<String, Object>>(){});
        body.setAll(requestMap);
        body.add("hashData", SHA512(requestMap));

        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<AccountResponseVo> response = restTemplate.postForEntity(URL, requestMessage, AccountResponseVo.class);

        // response data insert logic call
        log.info("이니시스 연결 완료 ------" + Objects.requireNonNull(response.getBody()).toString());
//        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
//                .trsnId()
//                .build();


        return null;
    }

    @Override
    public void cancelPay(OriginalOrderVo originalOrder) {
        log.info("-----------------Inicis cancelPay start");
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {
        log.info("-----------------Inicis netCancel start");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }

    private static String SHA512(Map<String, Object> map){
        List<String> objects =new LinkedList<>();
        objects.add("ItEQKi3rY7uvDS8l");
        objects.add(String.valueOf(map.get("type")));
        objects.add(String.valueOf(map.get("paymethod")));
        objects.add(String.valueOf(map.get("timestamp")));
        objects.add(String.valueOf(map.get("clientIp")));
        objects.add(String.valueOf(map.get("mid")));
        objects.add(String.valueOf(map.get("moid")));
        objects.add(String.valueOf(map.get("price")));

        String salt = String.join( "", objects);
        String hex = "";
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-512");
            msg.update(salt.getBytes());
            hex = String.format("%0128x", new BigInteger(1, msg.digest()));
        }catch (Exception e){
            log.info("SHA512 ERROR=" +  e);
        }
        return hex;
    }
}
