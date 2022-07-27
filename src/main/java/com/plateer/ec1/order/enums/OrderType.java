package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.code.order.OPT0001;
import com.plateer.ec1.common.code.order.OPT0002;
import com.plateer.ec1.order.validator.OrderPaymentValidators;
import com.plateer.ec1.order.validator.OrderProductValidators;
import com.plateer.ec1.order.validator.OrderTypeValidators;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@AllArgsConstructor
public enum OrderType implements Predicate<OrderValidationVo> {
    FO_GENERAL(OPT0001.GENERAL, OPT0002.FO,
            OrderProductValidators.isExistProduct.and(OrderProductValidators.isSellingProduct)
                    .and(OrderTypeValidators.isGeneralProduct).and(OrderTypeValidators.isGeneralValid)),
    BO_GENERAL(OPT0001.GENERAL, OPT0002.BO,
            OrderProductValidators.isExistProduct.and(OrderProductValidators.isSellingProduct)
                    .and(OrderTypeValidators.isGeneralProduct).and(OrderTypeValidators.isGeneralValid)),
    FO_ECOUPON(OPT0001.ECOUPON, OPT0002.FO,
            OrderProductValidators.isExistProduct.and(OrderProductValidators.isSellingProduct)
                    .and(OrderTypeValidators.isEcouponProduct).and(OrderTypeValidators.isEcouponValid).and(OrderTypeValidators.isEcouponOrdDvpValid)),
    BO_ECOUPON(OPT0001.ECOUPON, OPT0002.BO,
            OrderProductValidators.isExistProduct.and(OrderProductValidators.isSellingProduct)
                    .and(OrderTypeValidators.isEcouponProduct).and(OrderTypeValidators.isEcouponValid).and(OrderTypeValidators.isEcouponOrdDvpValid));

    private OPT0001 systemCode;
    private OPT0002 orderCode;
    private final Predicate<OrderValidationVo> validCheck;

    @Override
    public boolean test(OrderValidationVo t) {
        return PaymentType.INICIS.name().equals(t.getOrderRequestVo().getOrdPayInfoVo().getPaymentType()) ?
                validCheck.and(OrderPaymentValidators.isPaymentValid).test(t) : validCheck.test(t);
    }

    public static OrderType get(OrderRequestVo orderRequest){
        return Arrays.stream(values())
                    .filter((t) -> t.systemCode.getType().equals(orderRequest.getOrdBaseVo().getOrdSysCcd()) && t.orderCode.getType().equals(orderRequest.getOrdBaseVo().getOrdTpCd()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(""));
    };
}
