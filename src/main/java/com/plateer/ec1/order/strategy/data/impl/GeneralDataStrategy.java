package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.enums.DataType;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeneralDataStrategy implements DataStrategy {
    @Override
    public OrderVo create(OrderRequestVo orderRequest, OrderProductViewVo orderProductView) {
        log.info("-----------------general data create start-----------------");
        return null;
    }

    @Override
    public DataType getType() {
        return DataType.GENERAL;
    }
}
