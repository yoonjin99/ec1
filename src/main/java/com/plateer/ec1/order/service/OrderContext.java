package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.order.enums.history.OPT0012Type;
import com.plateer.ec1.order.mapper.data.OrderDataTrxMapper;
import com.plateer.ec1.order.mapper.validator.OrderValidatiorMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.OrderInfoVo;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderContext {
    private final OrderHistoryService orderHistoryService;
    private final PaymentService paymentService;

    private final OrderValidatiorMapper validatiorMapper;
    private final OrderDataTrxMapper orderDataTrxMapper;


    public void execute(DataStrategy dataStrategy, AfterStrategy afterStrategy, OrderRequestVo orderRequest){
        log.info("--------------OrderContext execute start");
        // 주문 모니터링 등록
//        int historyNo = orderHistoryService.insertOrderHistory(orderRequest);
        OrderVo dto = OrderVo.builder().build();
        try {
            log.info("orderRequest : {}", orderRequest);
            // 유효성 검증
            List<OrderProductViewVo> prd = validationCheck(orderRequest);
            dto.setProcCcd(OPT0012Type.FV);

            // 데이터 생성
            dto = dataStrategy.create(orderRequest, prd);
            dto.setProcCcd(OPT0012Type.FD);

            log.info(dto.toString() + "값!!!!!!!!");

            // 결제
//            paymentCall(orderRequest);
//            dto.setProcCcd(OPT0012Type.FP);
//
//            // 데이터 등록
//            insertOrderData(dto);
//
//            // 금액검증
//            amountValidation(orderRequest.getOrdNo());
//
//            // 후처리
//            afterStrategy.call(orderRequest, dto);
//            dto.setProcCcd(OPT0012Type.S);

        }catch (Exception e){
            log.error( "error : " + e);
            throw e;
        } finally {
            // 주문 모니터링 업데이트
//            orderHistoryService.updateOrderHistory(historyNo, dto);
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

    private void paymentCall(OrderRequestVo orderRequest){
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

        paymentService.approve(orderInfoVo, payInfo);
    }

    private void amountValidation(String orderNo){
        log.info("--------------amountValidation start");
    }

    @Transactional
    protected void insertOrderData(OrderVo vo){
        log.info("--------------insertOrderData start");
        orderDataTrxMapper.insertOrderBase(vo.getOpOrdBaseModel());
        orderDataTrxMapper.insertOrderGoods(vo.getOpGoodsInfoList());
        orderDataTrxMapper.insertOrderClaim(vo.getOpClmInfoModelList());
        orderDataTrxMapper.insertOrderAreaInfo(vo.getOpDvpAreaInfo());
        orderDataTrxMapper.insertOrderDvpInfo(vo.getOpDvpInfoList());

//        for(OpOrdBnfInfoModel bnf : vo.getOpOrdBnfInfoModelList()){
//            String key = orderDataTrxMapper.insertOrderBnf(vo.getOpOrdBnfInfoModelList());
//
//            for(OpOrdBnfRelInfoModel rel : vo.getOpOrdBnfRelInfoModelList()){
//                if()
//                orderDataTrxMapper.insertOrderBnfRel(vo.getOpOrdBnfRelInfoModelList());
//            }
//        }

        orderDataTrxMapper.insertOrderCost(vo.getOpOrdCostInfoModelList());
    }
}
