package com.plateer.ec1.payment.controller;

import com.plateer.ec1.payment.enums.PGIPType;
import com.plateer.ec1.payment.service.PaymentNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentNoticeService paymentNoticeService;

    @PostMapping(value = "/payment/depositNotice")
    public String depositNotice(HttpServletRequest request, @RequestBody MultiValueMap<String, String> param){
        log.info("가상계좌 입금 통보 param : {}", param);
        String getIp = request.getHeader("X-FORWARDED-FOR").equals("") ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR") ;

        return paymentNoticeService.INIPayNotice(param, getIp);
    }
}
