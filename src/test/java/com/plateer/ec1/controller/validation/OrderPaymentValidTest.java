package com.plateer.ec1.controller.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderPaymentValidTest {
    @Test
    @DisplayName("가상계좌 환불 필수값 확인")
    void refundValidTest(){
        // 1. 환불계좌번호 없음
        // 2. 환불은행코드 없음
        // 3. 환불예금주명 없음
    }
}
