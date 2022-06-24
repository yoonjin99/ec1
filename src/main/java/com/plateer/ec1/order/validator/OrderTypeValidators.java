package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class OrderTypeValidators {

    // 모바일 쿠폰 상품확인
    public static Predicate<OrderValidationVo> isEcouponOrderAbleProduct= (dto) -> {
        log.info("OrderTypeValidators 모바일 쿠폰 상품확인 : {}", dto);
        return true;
    };

    // 모바일 쿠폰 필수 데이터 확인
    public static Predicate<OrderValidationVo> ecouponDataValidation= (dto) -> {
        log.info("OrderTypeValidators 모바일 쿠폰 필수 데이터 확인: {}", dto);
        return true;
    };

    // 일반주문 상품확인
    public static Predicate<OrderValidationVo> isGeneralOrderAbleProduct = (dto) -> {
        log.info("OrderTypeValidator 일반주문 상품확인: {}", dto);
        return true;
    };

    // 일반주문 필수 데이터 확인
    public static Predicate<OrderValidationVo> generalDataValidation = (dto) -> {
        log.info("OrderTypeValidator 일반주문 필수 데이터 확인: {}", dto);
        return true;
    };
}
