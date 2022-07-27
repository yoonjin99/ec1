package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.enums.history.OPT0012Type;
import com.plateer.ec1.order.mapper.validator.OrderValidatiorMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.validator.OrderPaymentValidators;
import com.plateer.ec1.order.validator.OrderProductValidators;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.PayInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class OrderContext {
    private final OrderHistoryService orderHistoryService;
    private final PaymentService paymentService;

    private OrderValidatiorMapper validatiorMapper;

    OrderContext(OrderHistoryService orderHistoryService, PaymentService paymentService){
        this.orderHistoryService = orderHistoryService;
        this.paymentService = paymentService;
    }

    public void execute(DataStrategy dataStrategy, AfterStrategy afterStrategy, OrderRequestVo orderRequest){
        log.info("--------------OrderContext execute start");
        // 주문 모니터링 등록
        int historyNo = orderHistoryService.insertOrderHistory(orderRequest);
        OrderVo dto = null;
        try {
            log.info("orderRequest : {}", orderRequest);
            // 유효성 검증
            validationCheck(orderRequest);
            dto.setProcCcd(OPT0012Type.FV);

            // 데이터 생성
            dto = dataStrategy.create(orderRequest, new OrderProductViewVo());
            dto.setProcCcd(OPT0012Type.FD);
            ;
            // 결제
            PayInfoVo payInfo = new PayInfoVo();
            payInfo.setPaymentType(PaymentType.valueOf(orderRequest.getOrdPayInfoVo().getPaymentType()));
            dto.setProcCcd(OPT0012Type.FP);
//            paymentService.approve(payInfo);

            // 데이터 등록
            insertOrderData(dto);

            // 금액검증
            amountValidation(orderRequest.getOrdNo());

            // 후처리
            afterStrategy.call(orderRequest, dto);

            dto.setProcCcd(OPT0012Type.S);

        }catch (Exception e){
            log.error( "error : " + e);
        } finally {
            // 주문 모니터링 업데이트
            orderHistoryService.updateOrderHistory(historyNo, dto);
        }
        log.info("--------------OrderContext execute end");
    }

    private void validationCheck(OrderRequestVo orderRequest){
        OrderValidationVo validationDto = OrderValidationVo.createVo(orderRequest, selectGoods(orderRequest));
        boolean result = OrderType.get(orderRequest).test(validationDto);
        if(!result) throw new IllegalArgumentException("유효성 검증에 실패하였습니다.");
    }

    private List<PrGoodsBaseModel> selectGoods(OrderRequestVo vo){
        return validatiorMapper.selectGoodsBase(vo);
    }

    private void amountValidation(String orderNo){
        log.info("--------------amountValidation start");
    }

    private void insertOrderData(OrderVo orderDto){
        log.info("--------------insertOrderData start");
    }
}
