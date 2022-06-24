package com.plateer.ec1.order.vo;

import lombok.Data;

@Data
public class OrderRequestVo {
    private String orderType;
    private String systemType;
    private Long price;
    private String productId;
    private String orderNo;
    private String paymentType;
}
