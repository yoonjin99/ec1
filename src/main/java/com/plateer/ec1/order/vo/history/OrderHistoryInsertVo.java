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
    private String procCcd;

    public static <T> OrderHistoryInsertVo createData(T orderRequest, String ordNo, String clmNo) {
        return OrderHistoryInsertVo.builder()
                .ordNo(ordNo)
                .clmNo(clmNo)
                .reqParam(JsonFileReader.parseToJson(orderRequest))
                .build();
    }
}
