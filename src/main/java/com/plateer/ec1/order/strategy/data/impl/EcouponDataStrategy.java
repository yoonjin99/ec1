package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EcouponDataStrategy implements DataStrategy {
    @Override
    public OrderVo create(OrderRequestVo orderRequest, OrderProductViewVo orderProductView) {
        log.info("-----------------ecoupon data create start-----------------");
        return null;
    }
}
