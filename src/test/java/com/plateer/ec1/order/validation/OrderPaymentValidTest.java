package com.plateer.ec1.order.validation;

import com.plateer.ec1.order.validator.OrderPaymentValidators;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderPaymentValidTest {
    @Test
    @DisplayName("가상계좌 환불 필수값 확인")
    void refundValidTest(){
        // 1. 환불계좌번호 없음
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/valid/paymentValid/noRefundAccount.json", OrderRequestVo.class);

        OrderValidationVo vo = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo)
                .build();
        Assertions.assertFalse(OrderPaymentValidators.isPaymentValid.test(vo));

        // 2. 환불은행코드 없음
        OrderRequestVo orderRequestVo2 = JsonFileReader.getObject("json/order/valid/paymentValid/noRefundBankCd.json", OrderRequestVo.class);

        OrderValidationVo vo2 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo2)
                .build();
        Assertions.assertFalse(OrderPaymentValidators.isPaymentValid.test(vo2));

        // 3. 환불예금주명 없음
        OrderRequestVo orderRequestVo3 = JsonFileReader.getObject("json/order/valid/paymentValid/noRefundName.json", OrderRequestVo.class);

        OrderValidationVo vo3 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo3)
                .build();
        Assertions.assertFalse(OrderPaymentValidators.isPaymentValid.test(vo3));

        // 조건 충족
        OrderRequestVo orderRequestVo4 = JsonFileReader.getObject("json/order/valid/paymentValid/refundValidOk.json", OrderRequestVo.class);

        OrderValidationVo vo4 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo4)
                .build();
        Assertions.assertTrue(OrderPaymentValidators.isPaymentValid.test(vo4));
    }
}
