package com.plateer.ec1.order.vo;

import lombok.Data;

@Data
public class OrdPayInfoVo {
    private String paymentType;
    private Long price;
    private String bankCode;
    private String nmInput;
}
