package com.plateer.ec1.payment.factory.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.common.code.order.payment.OPT0009Code;
import com.plateer.ec1.common.code.order.payment.OPT0010Code;
import com.plateer.ec1.common.code.order.payment.OPT0011Code;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.common.AESUtil;
import com.plateer.ec1.payment.enums.*;
import com.plateer.ec1.payment.factory.PaymentTypeService;
import com.plateer.ec1.payment.mapper.PaymentInicisMapper;
import com.plateer.ec1.payment.mapper.PaymentInicisTrxMapper;
import com.plateer.ec1.payment.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public ApproveResVo approvePay(OrderInfoVo orderInfoVo, PayInfoVo payInfo) {
        log.info("-----------------Inicis approvePay start");
        AccountVo vo = createVo(orderInfoVo, payInfo);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = inicisApiCall(vo);
        RestTemplate restTemplate = new RestTemplate();
        AccountResponseVo response = restTemplate.postForEntity(InicisUrlType.PAYMENT_URL.getUrl(), httpEntity, AccountResponseVo.class).getBody();
        if(Objects.requireNonNull(response).getResultCode().equals("00")){
            insertPayInfo(response, orderInfoVo);
        }
        return ApproveResVo.create(response.getPrice());
    }

    private AccountVo createVo(OrderInfoVo orderInfoVo, PayInfoVo payInfo){
        StringBuilder sb = new StringBuilder();
        sb.append(InicisCommonType.KEY.getStr());
        sb.append(PayRefundType.PAY.getType());
        sb.append(InicisCommonType.VACCT.getStr());
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(clientIpCheck());
        sb.append(InicisCommonType.MID.getStr());
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
            OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
                    .payNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .trsnId(vo.getTid())
                    .ordNo(orderInfoVo.getOrdNo())
                    .payCcd(OPT0010Code.PAY.getType())
                    .payPrgsScd(OPT0011Code.REQUESTPAY.getType())
                    .payMnCd(OPT0009Code.POINT.getType())
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
    @Transactional
    public void cancelPay(PaymentCancelRequestVo paymentCancelRequestVo) {
        log.info("-----------------Inicis cancelPay start");
        // 취소금액이랑 결제 금액이 같은지 다른지
        CancelInfoVo info = inicisMapper.selectPayInfo(paymentCancelRequestVo);
        if(!Objects.isNull(info)){
            if(info.getOpPayInfoModel().getPayPrgsScd().equals(OPT0011Code.REQUESTPAY.getType())){ // 결제 전
                beforeDeposit(info, paymentCancelRequestVo);
            }else{ // 결제 후
                try {
                    afterDeposit(info, paymentCancelRequestVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 결제 전 취소
    private void beforeDeposit(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo){
        log.info("결제 전 취소 로직 --------------");
        OpPayInfoModel cancelCompleteData = insertCancelData(infoVo, paymentCancelRequestVo);

        long cancelPrice = infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice();
        if(cancelPrice > 0){
            log.info("입금 전 부분취소 로직 시작!");
            // 부분취소
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setOrdNo(cancelCompleteData.getOrdNo());
            orderInfoVo.setGoodName(infoVo.getGoodsNm());
            orderInfoVo.setBuyerName(infoVo.getOrdNm());
            orderInfoVo.setBuyerEmail("dbswls1999@naver.com");

            PayInfoVo payInfo = new PayInfoVo();
            payInfo.setBankCode(cancelCompleteData.getVrBnkCd());
            payInfo.setPrice(cancelPrice);
            payInfo.setNmInput(infoVo.getRfndAcctOwnNm());
            approvePay(orderInfoVo, payInfo);
        }
    }

    private OpPayInfoModel insertCancelData(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo){
        OpPayInfoModel cancelCompleteData  = infoVo.getOpPayInfoModel();
        cancelCompleteData.setClmNo(paymentCancelRequestVo.getClmNo());
        cancelCompleteData.setPayCcd(OPT0010Code.CANCEL.getType());
        cancelCompleteData.setPayPrgsScd(OPT0011Code.CANCEL.getType());
        cancelCompleteData.setPayNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        inicisTrxMapper.insertPayinfo(cancelCompleteData);

        return cancelCompleteData;
    }

    // 결제 후 취소
    private void afterDeposit(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo) throws Exception {
        long cancelPrice = infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice();
        if(cancelPrice > 0){ // 부분환불
            CancelRequestVo cancelRequestVo = createPartRefundVo(infoVo, paymentCancelRequestVo);

            RestTemplate restTemplate = new RestTemplate();
            PartCancelResponseVo responseVo = restTemplate.postForEntity(InicisUrlType.REFUND_URL.getUrl(), inicisApiCall(cancelRequestVo), PartCancelResponseVo.class).getBody();

            if("00".equals(Objects.requireNonNull(responseVo).getResultCode())){
                insertRefund(infoVo, paymentCancelRequestVo, responseVo.getTid());
            }else throw new Exception(responseVo.getResultMsg());
        }else{ // 전체 환불
            CancelRequestVo cancelRequestVo = createAllRefundVo(infoVo, paymentCancelRequestVo);

            RestTemplate restTemplate = new RestTemplate();
            CancelResponseVo responseVo = restTemplate.postForEntity(InicisUrlType.REFUND_URL.getUrl(), inicisApiCall(cancelRequestVo), CancelResponseVo.class).getBody();

            if("00".equals(Objects.requireNonNull(responseVo).getResultCode())){
                insertRefund(infoVo, paymentCancelRequestVo, "");
            }else throw new Exception(responseVo.getResultMsg());
        }
    }

    private void insertRefund(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo, String tid){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder()
                .payNo("S" +  LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .ordNo(infoVo.getOpPayInfoModel().getOrdNo())
                .clmNo(paymentCancelRequestVo.getClmNo())
                .payCcd(OPT0010Code.CANCEL.getType())
                .payPrgsScd(OPT0011Code.CANCEL.getType())
                .payMnCd(OPT0009Code.VRACCOUNT.getType())
                .vrAcct(infoVo.getOpPayInfoModel().getVrAcct())
                .vrAcctNm(infoVo.getOpPayInfoModel().getVrAcctNm())
                .vrBnkCd(infoVo.getOpPayInfoModel().getVrBnkCd())
                .payAmt(paymentCancelRequestVo.getCancelPrice())
                .orgPayNo(infoVo.getOpPayInfoModel().getPayNo())
                .rfndAvlAmt(0L)
                .cnclAmt(0L)
                .build();
        if(!tid.equals("")) opPayInfoModel.setTrsnId(tid);
        inicisTrxMapper.insertPayinfo(opPayInfoModel);
    }

    private CancelRequestVo createPartRefundVo(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(InicisCommonType.KEY.getStr());
        sb.append(PayRefundType.PARTREFUND.getType());
        sb.append(InicisCommonType.VACCT.getStr());
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(clientIpCheck());
        sb.append(InicisCommonType.MID.getStr());
        sb.append(infoVo.getOpPayInfoModel().getTrsnId());
        sb.append(paymentCancelRequestVo.getCancelPrice());
        sb.append(infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice());
        AESUtil aesUtil = new AESUtil(AESType.SECRETKEY.getStr(), AESType.IV.getStr());
        sb.append(aesUtil.AesEncode(infoVo.getRfndAcctNo()));

        String hashData = SHA512(sb.toString());

        return CancelRequestVo.builder()
                .type(PayRefundType.PARTREFUND.getType())
                .paymethod(InicisCommonType.VACCT.getStr())
                .timestamp(LocalDateTime.now())
                .clientIp(clientIpCheck())
                .mid(InicisCommonType.MID.getStr())
                .tid(infoVo.getOpPayInfoModel().getTrsnId())
                .price(paymentCancelRequestVo.getCancelPrice())
                .confirmPrice(infoVo.getOpPayInfoModel().getPayAmt() - paymentCancelRequestVo.getCancelPrice())
                .refundAcctNum(aesUtil.AesEncode(infoVo.getRfndAcctNo()))
                .refundBankCode(infoVo.getRfndBnkCk())
                .refundAcctName(infoVo.getRfndAcctOwnNm())
                .hashData(hashData)
                .build();
    }

    private CancelRequestVo createAllRefundVo(CancelInfoVo infoVo, PaymentCancelRequestVo paymentCancelRequestVo) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(InicisCommonType.KEY.getStr());
        sb.append(PayRefundType.REFUND.getType());
        sb.append(InicisCommonType.VACCT.getStr());
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        sb.append(clientIpCheck());
        sb.append(InicisCommonType.MID.getStr());
        sb.append(infoVo.getOpPayInfoModel().getTrsnId());

        AESUtil aesUtil = new AESUtil("ItEQKi3rY7uvDS8l", "HYb3yQ4f65QL89==");
        sb.append(aesUtil.AesEncode(infoVo.getRfndAcctNo()));

        String hashData = SHA512(sb.toString());

        return CancelRequestVo.builder()
                .type(PayRefundType.REFUND.getType())
                .paymethod(InicisCommonType.VACCT.getStr())
                .timestamp(LocalDateTime.now())
                .clientIp(clientIpCheck())
                .mid(InicisCommonType.MID.getStr())
                .tid(infoVo.getOpPayInfoModel().getTrsnId())
                .refundAcctNum(aesUtil.AesEncode(infoVo.getRfndAcctNo()))
                .refundBankCode(infoVo.getRfndBnkCk())
                .refundAcctName(infoVo.getRfndAcctOwnNm())
                .hashData(hashData)
                .build();
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
