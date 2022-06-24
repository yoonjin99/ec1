package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class OrderCommonValidators {

    public static Predicate<OrderValidationVo> validateMaxPurchaseCount = (dto) -> {
        log.info("OrderCommonValidators.validateMaxPurchaseCount: {}", dto);
        return true;
    };

    public static Predicate<OrderValidationVo> validateMinPurchaseCount= (dto) -> {
        log.info("OrderCommonValidators.validateMinPurchaseCount: {}", dto);
        return true;
    };

}
