package com.plateer.ec1.order.vo.history;

import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.util.JsonFileReader;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Builder
@Slf4j
public class OrderHistoryInsertVo {
    private String reqParam;
    private String ordNo;
    private String clmNo;

    public static OrderHistoryInsertVo createData(OrderRequestVo orderRequest) {
        return OrderHistoryInsertVo.builder()
                .ordNo(orderRequest.getOrdNo())
                .reqParam(JsonFileReader.parseToJson(orderRequest))
                .build();
    }
}
