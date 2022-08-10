package com.plateer.ec1.order.mapper.history;

import com.plateer.ec1.order.vo.history.OrderHistoryInsertVo;
import com.plateer.ec1.order.vo.history.OrderHistoryUpdateVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderHistoryTrxMapper {
    Long insertHistoryLog(OrderHistoryInsertVo insertVo);
    Long updateHistoryLog(OrderHistoryUpdateVo updateVo);
}
