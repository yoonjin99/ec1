package com.plateer.ec1.order.strategy.after;

import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderRequestVo;

public interface AfterStrategy {
    public void call(OrderRequestVo orderRequest, OrderVo orderDto);
}
