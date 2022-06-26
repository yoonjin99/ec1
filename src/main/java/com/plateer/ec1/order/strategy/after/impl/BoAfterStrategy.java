package com.plateer.ec1.order.strategy.after.impl;

import com.plateer.ec1.order.enums.AfterProcessType;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BoAfterStrategy implements AfterStrategy {
    @Override
    public void call(OrderRequestVo orderRequest, OrderVo orderDto) {
        log.info("BO 후처리 호출");
    }

    @Override
    public AfterProcessType getType() {
        return AfterProcessType.BO;
    }
}
