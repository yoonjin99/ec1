package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.common.model.product.PrItemInfoModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderProductViewVo {
    private PrGoodsBaseModel prGoodsBaseModel;
    private PrItemInfoModel prItemInfoModel;
}
