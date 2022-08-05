package com.plateer.ec1.order.mapper.validator;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.vo.OrdGoodsInfoVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderValidatiorMapper {
    List<OrderProductViewVo> selectGoodsBase(List<OrdGoodsInfoVo> ordGoodsInfoVo);
    boolean paymentCheck(Long totalPirce);
}
