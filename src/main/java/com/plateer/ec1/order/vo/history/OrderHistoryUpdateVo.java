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
    private Long logSeq;
    private String procCcd;

    public static <T> OrderHistoryUpdateVo createData(Long historyNo, T orderVo){
        return OrderHistoryUpdateVo.builder()
                .insData(JsonFileReader.parseToJson(orderVo))
                .logSeq(historyNo)
                .build();
    }
}
