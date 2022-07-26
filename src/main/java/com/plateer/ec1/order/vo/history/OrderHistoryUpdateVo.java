package com.plateer.ec1.order.vo.history;

import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.util.JsonFileReader;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderHistoryUpdateVo {
    private String insData;
    private int logSeq;
    private String procCcd;

    public static OrderHistoryUpdateVo createData(int historyNo, OrderVo orderVo){
        return OrderHistoryUpdateVo.builder()
                .insData(JsonFileReader.parseToJson(orderVo))
                .logSeq(historyNo)
                .procCcd(orderVo.getProcCcd().name())
                .build();
    }
}
