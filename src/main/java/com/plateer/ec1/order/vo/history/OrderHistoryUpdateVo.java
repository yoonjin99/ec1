package com.plateer.ec1.order.vo.history;

import com.plateer.ec1.order.vo.OrderVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderHistoryUpdateVo {
    private OrderVo uptData;
    private int logSeq;
    private String procCcd;

    public static OrderHistoryUpdateVo createData(int historyNo, OrderVo orderVo){
        return OrderHistoryUpdateVo.builder()
                .uptData(orderVo)
                .logSeq(historyNo)
                .procCcd("")
                .build();
    }
}
