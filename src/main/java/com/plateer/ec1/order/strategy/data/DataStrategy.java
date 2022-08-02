package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.order.enums.DataType;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;

import java.util.List;

public interface DataStrategy {
    OrderVo create(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView);
    DataType getType();
}
