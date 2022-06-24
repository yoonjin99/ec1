package com.plateer.ec1.order.strategy.after.impl;

import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FoAfterStrategy implements AfterStrategy {
    @Override
    public void call(OrderRequestVo orderRequest, OrderVo orderDto) {
        log.info("FO 후처리 호출");
    }
}
