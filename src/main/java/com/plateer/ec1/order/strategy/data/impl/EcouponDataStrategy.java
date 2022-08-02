package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.enums.DataType;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EcouponDataStrategy implements DataStrategy {
    @Override
    public OrderVo create(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView) {
        log.info("-----------------ecoupon data create start-----------------");
        return OrderVo.builder()
                .opOrdBaseModel(OpOrdBaseModel.createGeneralData(orderRequest))
                .opGoodsInfoList(OpGoodsInfo.createGeneralData(orderRequest,orderProductView))
                .opClmInfoModelList(OpClmInfoModel.createGeneralData(orderRequest, orderProductView))
                .opDvpAreaInfo(OpDvpAreaInfo.createGeneralData(orderRequest))
                .opDvpInfoList(OpDvpInfo.createGeneralData(orderRequest))
                .opOrdCostInfoModelList(OpOrdCostInfoModel.createGeneralData(orderRequest))
                .opOrdBnfRelInfoModelList(OpOrdBnfRelInfoModel.createGeneralData(orderRequest))
                .opOrdBnfInfoModelList(OpOrdBnfInfoModel.createGeneralData(orderRequest))
                .build();
    }

    @Override
    public DataType getType() {
        return DataType.ECOUPON;
    }
}
