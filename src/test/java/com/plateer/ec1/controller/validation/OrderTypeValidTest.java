package com.plateer.ec1.controller.validation;

import com.plateer.ec1.order.validator.OrderProductValidators;
import com.plateer.ec1.order.validator.OrderTypeValidators;
import com.plateer.ec1.order.vo.OrderValidationVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTypeValidTest {

    @Test
    @DisplayName("ecoupon 상품이 맞는지 확인")
    void isEcouponProductTest(){
        OrderValidationVo vo = OrderValidationVo.builder()
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));
    }

    @Test
    @DisplayName("ecoupon 필수 데이터 확인")
    void isEcouponValidTest(){
        // 휴대폰 번호가 있는지 확인
        OrderValidationVo vo = OrderValidationVo.builder()
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));
    }

    @Test
    @DisplayName("배송지가 1개 or 배송지수 = 상품개수 확인")
    void isEcouponOrdDvpValidTest(){
        // 1. 배송지가 1개일때

        // 2. 배송지가 여러개인데 상품 개수랑 다름

        // 3. 배송지랑 상품개수랑 같음
        OrderValidationVo vo = OrderValidationVo.builder()
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));
    }

    @Test
    @DisplayName("일반상품이고 직배송인지 확인")
    void isGeneralProductTest(){
        // 1. 일반상품이 아님

        // 2. 직배송이 아님

        // 3. 1,2 해당함
        OrderValidationVo vo = OrderValidationVo.builder()
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));
    }

    @Test
    @DisplayName("일반주문 필수 데이터 확인")
    void isGeneralValidTest(){
        // 1. 수취인주소 없음

        // 2. 휴대폰번호 없음

        // 3. 수취인명 없음

        // 4. 1,2,3 충족함
        OrderValidationVo vo = OrderValidationVo.builder()
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));
    }
}
