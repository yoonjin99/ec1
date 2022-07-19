package com.plateer.ec1.payment.factory.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.common.code.order.OPT0009Code;
import com.plateer.ec1.common.code.order.OPT0010Code;
import com.plateer.ec1.common.code.order.OPT0011Code;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.mapper.PaymentInicisMapper;
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
    private final PaymentInicisMapper inicisMapper;
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
        AccountVo vo = createVo(orderInfoVo, payInfo);
        log.info("-----------------Inicis approvePay start");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = inicisApiCall(vo);
        RestTemplate restTemplate = new RestTemplate();
        AccountResponseVo response = restTemplate.postForEntity(URL, httpEntity, AccountResponseVo.class).getBody();
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

    private void insertPayInfo(AccountResponseVo vo, OrderInfoVo orderInfoVo){
        try {
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
    public void cancelPay(PaymentCancelRequestVo paymentCancelRequestVo) {
        log.info("-----------------Inicis cancelPay start");
        CancelInfoVo info = inicisMapper.selectPayInfo(paymentCancelRequestVo);
        // NULL 일 경우 환불 가능한 상품이 존재하지 않음.
        if(info.getOpPayInfoModel().getPayPrgsScd().equals(OPT0011Code.REQUESTPAY.getType())){ // 결제 전
            beforeDeposit(info, paymentCancelRequestVo);
        }else{ // 결제 후
            afterDeposit(info, paymentCancelRequestVo);
        }
    }

    // 결제 전 취소
    @Transactional
    protected void beforeDeposit(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo){
        log.info("결제 전 취소 로직 --------------");
        long cancelPrice = infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice();

        OpPayInfoModel cancelCompleteData  = infoVo.getOpPayInfoModel();
        cancelCompleteData.setClmNo(paymentCancelRequestVo.getClmNo());
        cancelCompleteData.setPayCcd(OPT0010Code.CANCEL.getType());
        cancelCompleteData.setPayPrgsScd(OPT0011Code.CANCEL.getType());
        cancelCompleteData.setPayNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        inicisTrxMapper.insertPayinfo(cancelCompleteData);

        if(cancelPrice > 0){
            log.info("입금 전 부분취소 로직 시작!");
            // 부분취소
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setOrdNo(cancelCompleteData.getOrdNo());
            orderInfoVo.setGoodName(infoVo.getGoodsNm());
            orderInfoVo.setBuyerName(infoVo.getOrdNm());
            PayInfoVo payInfo = new PayInfoVo();
            payInfo.setBankCode(cancelCompleteData.getVrBnkCd());
            payInfo.setPrice(cancelPrice);
            payInfo.setNmInput(infoVo.getRfndAcctOwnNm());
            approvePay(orderInfoVo, payInfo);
        }
//        paymentCancelRequestVo.setCancelPrice(opPayInfoModel.getPayAmt());
//        inicisTrxMapper.updateCancelResult(paymentCancelRequestVo);
    }

    // 결제 후 취소
    @Transactional
    protected void afterDeposit(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo){
        long cancelPrice = infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice();
        CancelRequestVo cancelRequestVo;
        // TODO: 취소 실패일 경우 어떻게 처리 ?.?
        StringBuilder sb = new StringBuilder();
        sb.append("ItEQKi3rY7uvDS8l");
        sb.append("PartialRefund");
        sb.append("Vacct");
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(clientIpCheck());
        sb.append("INIpayTest");
        sb.append(infoVo.getOpPayInfoModel().getTrsnId());

        if(cancelPrice > 0){ // 부분환불
            sb.append(paymentCancelRequestVo.getCancelPrice());
            sb.append(cancelPrice);
            sb.append(infoVo.getOpPayInfoModel().getVrAcct());

            String hashData = SHA512(String.valueOf(sb));

            cancelRequestVo = CancelRequestVo.builder()
                    .type("PartialRefund")
                    .paymethod("Vacct")
                    .timestamp(LocalDateTime.now())
                    .clientIp(clientIpCheck())
                    .mid("INIpayTest")
                    .price(paymentCancelRequestVo.getCancelPrice())
                    .confirmPrice(cancelPrice)
                    .refundAcctNum(infoVo.getRfndAcctNo())
                    .refundBankCode(infoVo.getRfndBnkCk())
                    .refundAcctName(infoVo.getRfndAcctOwnNm())
                    .hashData(hashData)
                    .build();

            RestTemplate restTemplate = new RestTemplate();
            PartCancelResponseVo responseVo = restTemplate.postForEntity("https://iniapi.inicis.com/api/v1/refund", inicisApiCall(cancelRequestVo), PartCancelResponseVo.class).getBody();
            log.info(responseVo + "값!!!!");
        }else{ // 전체 환불
            sb.append(infoVo.getOpPayInfoModel().getVrAcct());

            String hashData = SHA512(String.valueOf(sb));

            cancelRequestVo = CancelRequestVo.builder()
                    .type("Refund")
                    .paymethod("Vacct")
                    .timestamp(LocalDateTime.now())
                    .clientIp(clientIpCheck())
                    .mid("INIpayTest")
                    .refundAcctNum(infoVo.getRfndAcctNo())
                    .refundBankCode(infoVo.getRfndBnkCk())
                    .refundAcctName(infoVo.getRfndAcctOwnNm())
                    .hashData(hashData)
                    .build();

            RestTemplate restTemplate = new RestTemplate();
            CancelResponseVo responseVo = restTemplate.postForEntity("https://iniapi.inicis.com/api/v1/refund", inicisApiCall(cancelRequestVo), CancelResponseVo.class).getBody();
            log.info(responseVo + "값2222");
        }
//        inicisTrxMapper.insertPayinfo(opPayInfoModel);
    }

    @Override
    public void netCancel(NetCancelReqVo netCancelReqVO) {
        log.info("-----------------Inicis netCancel start");
    }

    private <T> HttpEntity<MultiValueMap<String, Object>> inicisApiCall(T t){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Map<String, Object> requestMap = objectMapper.convertValue(t, new TypeReference<Map<String, Object>>(){});
        body.setAll(requestMap);

        return new HttpEntity<>(body, httpHeaders);
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

    private String clientIpCheck(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        return ip;
    }

}
