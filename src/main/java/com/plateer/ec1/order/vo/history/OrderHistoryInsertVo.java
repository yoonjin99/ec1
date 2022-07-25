package com.plateer.ec1.order.vo.history;

import com.plateer.ec1.order.enums.history.OPT0012Type;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class OrderHistoryInsertVo {
    private OrderRequestVo insData;
    private String ordNo;
    private String clmNo;
    private String procCcd;

    public static OrderHistoryInsertVo createData(OrderRequestVo orderRequest){
        return OrderHistoryInsertVo.builder()
                .ordNo(orderRequest.getOrdNo())
                .insData(orderRequest)
                .procCcd(OPT0012Type.S.name())
                .build();
    }
}
