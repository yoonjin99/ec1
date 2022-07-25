package com.plateer.ec1.order.service;

import com.plateer.ec1.order.mapper.history.OrderHistoryTrxMapper;
import com.plateer.ec1.order.vo.history.OrderHistoryInsertVo;
import com.plateer.ec1.order.vo.history.OrderHistoryUpdateVo;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryService {

    private final OrderHistoryTrxMapper orderHistoryTrxMapper;

    @Transactional
    public int insertOrderHistory(OrderRequestVo orderRequest){
        log.info("------------------insertOrderHistory start");
        try {
            OrderHistoryInsertVo vo = OrderHistoryInsertVo.createData(orderRequest);
            return orderHistoryTrxMapper.insertHistoryLog(vo);
        }catch (Exception e){
            log.info("orderHistory insert error : {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void updateOrderHistory(int historyNo, OrderVo orderDto){
        log.info("------------------updateOrderHistory start");
        try {
            OrderHistoryUpdateVo vo = OrderHistoryUpdateVo.createData(historyNo, orderDto);
            orderHistoryTrxMapper.updateHistoryLog(vo);
        }catch (Exception e){
            log.info("orderHistory insert error : {}", e.getMessage());
            throw e;
        }
    }
}
