package com.plateer.ec1.order.service;

import com.plateer.ec1.order.enums.AfterProcessType;
import com.plateer.ec1.order.enums.DataType;
import com.plateer.ec1.order.mapper.data.OrderDataTrxMapper;
import com.plateer.ec1.order.mapper.validator.OrderValidatiorMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.after.impl.BoAfterStrategy;
import com.plateer.ec1.order.strategy.after.impl.FoAfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.strategy.data.impl.EcouponDataStrategy;
import com.plateer.ec1.order.strategy.data.impl.GeneralDataStrategy;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    private final OrderHistoryService orderHistoryService;
    private final PaymentService paymentService;
    private final OrderValidatiorMapper validatiorMapper;
    private final OrderDataTrxMapper orderDataTrxMapper;

    private final Map<DataType, DataStrategy> dataStrategyMap = new HashMap<>();
    private final Map<AfterProcessType, AfterStrategy> afterStrategyMap = new HashMap<>();


    public OrderService(OrderHistoryService orderHistoryService, PaymentService paymentService, List<DataStrategy> dataStrategies, List<AfterStrategy> afterStrategies, OrderValidatiorMapper validatiorMapper, OrderDataTrxMapper orderDataTrxMapper) {
        this.orderHistoryService = orderHistoryService;
        this.paymentService = paymentService;
        this.validatiorMapper = validatiorMapper;
        this.orderDataTrxMapper = orderDataTrxMapper;
        dataStrategies.forEach(dataStrategy -> dataStrategyMap.put(dataStrategy.getType(), dataStrategy));
        afterStrategies.forEach(afterStrategy -> afterStrategyMap.put(afterStrategy.getType(), afterStrategy));
    }

    public void order(OrderRequestVo orderRequest){
        log.info("-----------OrderService order start");
        OrderContext orderContext = new OrderContext(orderHistoryService, paymentService, validatiorMapper, orderDataTrxMapper);
        orderContext.execute(getDataStrategy(orderRequest), getAfterStrategy(orderRequest), orderRequest);
    }

    private DataStrategy getDataStrategy(OrderRequestVo orderRequest){
        log.info("-----------GetDataStrategy start");
        return dataStrategyMap.get(DataType.getDataType(orderRequest.getOrdBaseVo().getOrdTpCd()));
    }

    private AfterStrategy getAfterStrategy(OrderRequestVo orderRequest){
        log.info("-----------GetAfterStrategy start");
        return afterStrategyMap.get(AfterProcessType.valueOf(orderRequest.getOrdBaseVo().getOrdSysCcd()));
    }
}
