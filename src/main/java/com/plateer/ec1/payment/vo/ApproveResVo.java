package com.plateer.ec1.payment.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApproveResVo {
    // 성공여부
    // 결제금액
    private Long payPrice;
    // 승인시간

    public static ApproveResVo create(Long payPrice){
        return ApproveResVo.builder()
                .payPrice(payPrice)
                .build();
    }
}
