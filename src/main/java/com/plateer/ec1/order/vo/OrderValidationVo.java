package com.plateer.ec1.order.vo;


import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderValidationVo {
    private String orderType;
    private OrderRequestVo orderRequestVo;
    private List<OrderProductViewVo> prGoodsBaseModel;

    public static OrderValidationVo createVo(OrderRequestVo orderRequestVo, List<OrderProductViewVo> goodsBaseModel){
        return OrderValidationVo.builder()
                .orderType(orderRequestVo.getOrdBaseVo().getOrdTpCd())
                .orderRequestVo(orderRequestVo)
                .prGoodsBaseModel(goodsBaseModel)
                .build();
    }
}
