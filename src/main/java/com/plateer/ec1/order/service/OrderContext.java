package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.enums.history.OPT0012Type;
import com.plateer.ec1.order.mapper.data.OrderDataTrxMapper;
import com.plateer.ec1.order.mapper.validator.OrderValidatiorMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.ApproveResVo;
import com.plateer.ec1.payment.vo.OrderInfoVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderContext {
    private final OrderHistoryService orderHistoryService;
    private final PaymentService paymentService;

    private final OrderValidatiorMapper validatiorMapper;
    private final OrderDataTrxMapper orderDataTrxMapper;


    @Transactional(rollbackFor={Exception.class, RuntimeException.class})
    public void execute(DataStrategy dataStrategy, AfterStrategy afterStrategy, OrderRequestVo orderRequest) {
        log.info("--------------OrderContext execute start");
        // 주문 모니터링 등록
        int historyNo = orderHistoryService.insertOrderHistory(orderRequest);
        OrderVo dto = OrderVo.builder().build();
        try {
            log.info("orderRequest : {}", orderRequest);
            // 유효성 검증
            List<OrderProductViewVo> prd = validationCheck(orderRequest);
            dto.setProcCcd(OPT0012Type.FV);

            // 데이터 생성
            dto = dataStrategy.create(orderRequest, prd);
            dto.setProcCcd(OPT0012Type.FD);

            log.info("orderVo : {}", dto.toString());

            // 결제
            ApproveResVo approveResVo = paymentCall(orderRequest);
            dto.setProcCcd(OPT0012Type.FP);

            // TODO : 개선하기
            if(orderRequest.getOrdPayInfoVo().getPaymentType().equals("POINT")){ // 포인트 결제
                dto.getOpOrdBaseModel().setOrdCmtDtime(LocalDateTime.now());
                for(OpClmInfoModel model : dto.getOpClmInfoModelList()){
                    model.setOrdPrgsScd("20");
                    model.setOrdClmCmtDtime(LocalDateTime.now());
                }
            }

            // 데이터 등록
            insertOrderData(dto);

            // 금액검증
            amountValidation(orderRequest.getOrdNo());

            // 후처리
            afterStrategy.call(orderRequest, dto);
            dto.setProcCcd(OPT0012Type.S);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 주문 모니터링 업데이트
            orderHistoryService.updateOrderHistory(historyNo, dto);
        }
        log.info("--------------OrderContext execute end");
    }

    private List<OrderProductViewVo> validationCheck(OrderRequestVo orderRequest){
        log.info("유효성 검증 로직 시작");
        List<OrderProductViewVo> goodsBaseModels = selectGoods(orderRequest);
        OrderValidationVo validationDto = OrderValidationVo.createVo(orderRequest, goodsBaseModels);

        boolean result = OrderType.get(orderRequest).test(validationDto);
        if(!result) throw new IllegalArgumentException("유효성 검증에 실패하였습니다.");

        return goodsBaseModels;
    }

    private List<OrderProductViewVo> selectGoods(OrderRequestVo vo){
        log.info("상품 정보 조회");
        return validatiorMapper.selectGoodsBase(vo.getOrdGoodsInfoVo());
    }

    private ApproveResVo paymentCall(OrderRequestVo orderRequest){
        PayInfoVo payInfo = new PayInfoVo();
        payInfo.setPaymentType(PaymentType.valueOf(orderRequest.getOrdPayInfoVo().getPaymentType()));
        payInfo.setPrice(orderRequest.getOrdPayInfoVo().getPrice());
        payInfo.setBankCode(orderRequest.getOrdPayInfoVo().getBankCode());
        payInfo.setNmInput(orderRequest.getOrdPayInfoVo().getNmInput());

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrdNo(orderRequest.getOrdNo());
        orderInfoVo.setGoodName("상품이름");
        orderInfoVo.setBuyerEmail("dbswls1999@naver.com");
        orderInfoVo.setBuyerName(orderRequest.getOrdPayInfoVo().getNmInput());

        return paymentService.approve(orderInfoVo, payInfo);
    }

    public void insertOrderData(OrderVo vo){
        try {
            log.info("--------------insertOrderData start");
            orderDataTrxMapper.insertOrderBase(vo.getOpOrdBaseModel());
            orderDataTrxMapper.insertOrderGoods(vo.getOpGoodsInfoList());
            orderDataTrxMapper.insertOrderClaim(vo.getOpClmInfoModelList());
            orderDataTrxMapper.insertOrderAreaInfo(vo.getOpDvpAreaInfo());
            orderDataTrxMapper.insertOrderDvpInfo(vo.getOpDvpInfoList());

            // TODO : 개선하기
            for(OpOrdBnfInfoModel bnf : vo.getOpOrdBnfInfoModelList()){
                String key = orderDataTrxMapper.insertOrderBnf(bnf);

                for(OrderBenefitRelVo rel : vo.getOpOrdBnfRelInfoModelList()){
                    if(bnf.getCpnIssNo().equals(rel.getCpnIssNo()) && bnf.getOrdNo().equals(rel.getOrdNo())){
                        rel.setOrdBnfNo(key);
                        orderDataTrxMapper.insertOrderBnfRel(rel);
                    }
                }
            }
            orderDataTrxMapper.insertOrderCost(vo.getOpOrdCostInfoModelList());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void amountValidation(String ordNo) throws Exception{
        log.info("--------------금액 검증 로직 시작 ---------");
        // sum(주문상품금액) + sum(배송비용) - sum(혜택) = sum(결제)
        if(!validatiorMapper.paymentCheck(ordNo)){
            throw new Exception("금액 검증 실패"); // 데이터 롤백 해줘야함
        }
    }
}
