package com.plateer.ec1.payment.factory.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.common.code.order.OPT0009Code;
import com.plateer.ec1.common.code.order.OPT0010Code;
import com.plateer.ec1.common.code.order.OPT0011Code;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.mapper.PaymentInicisTrxMapper;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class InicisServiceImpl implements PaymentTypeService {

    private static final String URL = "https://iniapi.inicis.com/api/v1/formpay";
    private final PaymentInicisTrxMapper inicisTrxMapper;
    private final ObjectMapper objectMapper;

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }

    @Override
    public void validateAuth(PayInfoVo payInfo) {
        log.info("-----------------Inicis validateAuth start");
    }

    @Override
    @Transactional
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo, PayInfoVo payInfo) {
        log.info("-----------------Inicis approvePay start");
        AccountVo vo = createVo(orderInfoVo, payInfo);
        String response = inicisApiCall(vo);
        insertPayInfo(response, orderInfoVo);
        return new ApproveResVo();
    }

    private AccountVo createVo(OrderInfoVo orderInfoVo, PayInfoVo payInfo){
        StringBuilder sb = new StringBuilder();
        sb.append("ItEQKi3rY7uvDS8l");
        sb.append("Pay");
        sb.append("Vacct");
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(clientIpCheck());
        sb.append("INIpayTest");
        sb.append(orderInfoVo.getOrdNo());
        sb.append(payInfo.getPrice());

        String hashData = SHA512(sb.toString());

        return AccountVo.builder()
                .goodName(orderInfoVo.getGoodName())
                .buyerName(orderInfoVo.getBuyerName())
                .buyerEmail(orderInfoVo.getBuyerEmail())
                .price(payInfo.getPrice())
                .bankCode(payInfo.getBankCode())
                .nmInput(payInfo.getNmInput())
                .timestamp(LocalDateTime.now())
                .moid(orderInfoVo.getOrdNo())
                .dtInput(LocalDateTime.now().plusDays(1))
                .tmInput(LocalDateTime.now())
                .clientIp(clientIpCheck())
                .hashData(hashData)
                .build();
    }

    private String clientIpCheck(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        return ip;
    }

    private String inicisApiCall(AccountVo accountVo){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Map<String, Object> requestMap = objectMapper.convertValue(accountVo, new TypeReference<Map<String, Object>>(){});
        body.setAll(requestMap);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(URL, new HttpEntity<>(body, httpHeaders), String.class).getBody();
    }

    private void insertPayInfo(String response, OrderInfoVo orderInfoVo){
        try {
            AccountResponseVo vo = objectMapper.readValue(response, AccountResponseVo.class);

            if(!vo.getResultCode().equals("00")) throw new Exception(vo.getResultMsg());

            OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
                    .payNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .trsnId(vo.getTid())
                    .ordNo(orderInfoVo.getOrdNo())
                    .payCcd(OPT0009Code.VRACCOUNT.getType())
                    .payPrgsScd(OPT0011Code.REQUESTPAY.getType())
                    .payMnCd(OPT0010Code.PAY.getType())
                    .vrAcct(vo.getVacct())
                    .vrAcctNm(vo.getVacctName())
                    .vrBnkCd(vo.getVacctBankCode())
                    .vrValDt(vo.getValidDate())
                    .vrValTt(vo.getValidTime())
                    .payAmt(vo.getPrice())
                    .rfndAvlAmt(0L)
                    .cnclAmt(0L)
                    .build();
            inicisTrxMapper.insertPayinfo(opPayInfoModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelPay(OriginalOrderVo originalOrder) {
        log.info("-----------------Inicis cancelPay start");
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {
        log.info("-----------------Inicis netCancel start");
    }

    private String SHA512(String sb){
        String hex = "";
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-512");
            msg.update(sb.getBytes());
            hex = String.format("%0128x", new BigInteger(1, msg.digest()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return hex;
    }

}
