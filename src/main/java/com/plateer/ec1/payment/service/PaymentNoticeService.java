package com.plateer.ec1.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.payment.enums.InicisReturnType;
import com.plateer.ec1.payment.enums.PGIPType;
import com.plateer.ec1.payment.mapper.PaymentInicisTrxMapper;
import com.plateer.ec1.payment.vo.ININoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentNoticeService {

    private final PaymentInicisTrxMapper inicisTrxMapper;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Transactional
    public String INIPayNotice(MultiValueMap<String, String> noticeParam, String getIp){
        log.info("서비스 호출");
        String result = "FAiL";
        List<String> ipList = Stream.of(PGIPType.values())
                .map(PGIPType::getIp)
                .collect(Collectors.toList());
        for(String ip : ipList){
            if(ip.equals(getIp.substring(0,10))){
                result = updateDepostResult(noticeParam);
            }
        }
        return result;
    }

    private String  updateDepostResult(MultiValueMap<String, String> noticeParam){
        try {
            Map<String, String> requestParam = noticeParam.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, p -> p.getValue().get(0)));
            ININoticeVo vo = objectMapper.convertValue(requestParam, ININoticeVo.class);
            validate(vo);
            if(vo.getTypeMsg().equals(InicisReturnType.SUCCESS.getTypeMsg())) inicisTrxMapper.updateDepositResult(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    private void validate(ININoticeVo vo){
        Set<ConstraintViolation<ININoticeVo>> validVo = validator.validate(vo);
        if(!validVo.isEmpty()){
            String sb = validVo.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            throw new ConstraintViolationException("validation error : " + sb.toString(), validVo);
        }
    }
}
