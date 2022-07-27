package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProductValidators {

    public static Predicate<OrderValidationVo> isExistProduct = (vo) -> {
        log.info("OrderProductValidators.isExistProduct");
        return !Objects.isNull(vo.getPrGoodsBaseModel()) && vo.getPrGoodsBaseModel().size() == vo.getOrderRequestVo().getOrdGoodsInfoVo().size();
    };

    public static Predicate<OrderValidationVo> isSellingProduct = (vo) -> {
        log.info("OrderProductValidators.isSellingProduct");
        return vo.getPrGoodsBaseModel().stream().allMatch(goodsBaseModel -> goodsBaseModel.getPrgsStatCd().equals("20"));
    };
}
