package com.plateer.ec1.order.service;

import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.payment.factory.PaymentType;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.payment.vo.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
public class OrderContext {
    private OrderHistoryService orderHistoryService;
//    private PayService payService;

//    OrderContext(OrderHistoryService orderHistoryService, PayService payService){
//        this.orderHistoryService = orderHistoryService;
//        this.payService = payService;
//    }

    public void execute(DataStrategy dataStrategy, AfterStrategy afterStrategy, OrderRequestVo orderRequest){
        log.info("--------------OrderContext execute start");
        // 주문 모니터링 등록
        Long historyNo = orderHistoryService.insertOrderHistory(orderRequest);
        OrderVo dto = null;
        try {
            // 유효성 검증
            OrderValidationVo validationDto = new OrderValidationVo();
            validationDto.setOrderType("general");
            log.info("orderRequest : {}", orderRequest);
            OrderType.get(orderRequest).test(validationDto);

            // 데이터 생성
            dto = dataStrategy.create(orderRequest, new OrderProductViewVo());

            // 결제
//            PayInfo payInfo = new PayInfo();
//            payInfo.setPaymentType(PaymentType.valueOf(orderRequest.getPaymentType().toUpperCase(Locale.ROOT)));
//            payService.approve(payInfo);

            // 데이터 등록
            insertOrderData(dto);

            // 금액검증
            amountValidation(orderRequest.getOrderNo());

            // 재고차감
            deleteStock(dto);

            // FO일 경우 장바구니 사용처리
//            if(orderRequest.getSystemType().equals("FO")){
//                deleteCartData(dto);
//            }
            // 후처리
            afterStrategy.call(orderRequest, dto);

        }catch (Exception e){
            log.error( "error : " + e);
        } finally {
            // 주문 모니터링 업데이트
            orderHistoryService.updateOrderHistory(historyNo, dto);
        }
        log.info("--------------OrderContext execute end");
    }

    private void amountValidation(String orderNo){
        log.info("--------------amountValidation start");
    }

    private void insertOrderData(OrderVo orderDto){
        log.info("--------------insertOrderData start");
    }

    private void deleteCartData(OrderVo orderDto){
        log.info("-----------장바구니 사용처리");
    }
    
    private void deleteStock(OrderVo orderDto){
        log.info("-----------재고차감");
    }
}
