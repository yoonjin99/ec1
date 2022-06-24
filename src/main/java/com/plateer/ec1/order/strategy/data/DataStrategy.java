package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;

public interface DataStrategy {
    OrderVo create(OrderRequestVo orderRequest, OrderProductViewVo orderProductView);
}
