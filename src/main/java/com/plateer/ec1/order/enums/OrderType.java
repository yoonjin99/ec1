package com.plateer.ec1.order.enums;

import com.plateer.ec1.order.validator.OrderCommonValidators;
import com.plateer.ec1.order.validator.OrderTypeValidators;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@AllArgsConstructor
public enum OrderType implements Predicate<OrderValidationVo> {
    FO_GENERAL("FO", "general", OrderCommonValidators.validateMinPurchaseCount.and(OrderTypeValidators.generalDataValidation)),
    BO_GENERAL("BO", "general", OrderCommonValidators.validateMinPurchaseCount.and(OrderTypeValidators.generalDataValidation)),
    FO_ECOUPON("FO", "ecoupon", OrderCommonValidators.validateMinPurchaseCount.and(OrderTypeValidators.ecouponDataValidation)),
    BO_ECOUPON("BO", "ecoupon", OrderCommonValidators.validateMinPurchaseCount.and(OrderTypeValidators.ecouponDataValidation));

    private String systemCode;
    private String orderCode;
    private final Predicate<OrderValidationVo> validCheck;

    @Override
    public boolean test(OrderValidationVo t) {
        return validCheck.test(t);
    }

    public static OrderType get(OrderRequestVo orderRequest){
        return Arrays.stream(values())
                    .filter((t) -> t.systemCode.equals(orderRequest.getOrdBaseVo().getOrdSysCcd()) && t.orderCode.equals(orderRequest.getOrdBaseVo().getOrdTpCd()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(""));
    };
}
