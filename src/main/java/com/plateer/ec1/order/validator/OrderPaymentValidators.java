package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrdBaseVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.CustomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.function.Predicate;

@Slf4j
public class OrderPaymentValidators {
    public static Predicate<OrderValidationVo> isPaymentValid = (vo) -> {
        log.info("OrderProductValidators.isPaymentValid");
        // 환불계좌번호, 환불은행코드, 환불예금주명
        return CustomStringUtils.isNullOrEmpty(vo.getOrderRequestVo().getOrdBaseVo().getRfndAcctNo(),
                vo.getOrderRequestVo().getOrdBaseVo().getRfndAcctOwnNm(),
                vo.getOrderRequestVo().getOrdPayInfoVo().getBankCode());
    };

}


