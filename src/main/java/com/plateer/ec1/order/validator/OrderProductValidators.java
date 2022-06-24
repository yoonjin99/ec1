package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class OrderProductValidators {
    public static Predicate<OrderValidationVo> isSellingProduct = (dto) -> {
        log.info("OrderProductValidators.isSellingProduct: {}", dto);
        return true;
    };
}
