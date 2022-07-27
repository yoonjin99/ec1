package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class OrderPaymentValidators {
    public static Predicate<OrderValidationVo> isPaymentValid = (vo) -> {
        log.info("OrderProductValidators.isPaymentValid");
        // 환불계좌번호, 환불은행코드, 환불예금주명
        return !vo.getOrderRequestVo().getOrdBaseVo().getRfndAcctNo().equals("")
                && !vo.getOrderRequestVo().getOrdBaseVo().getRfndAcctOwnNm().equals("")
                && !vo.getOrderRequestVo().getOrdPayInfoVo().getBankCode().equals("");
    };

}
