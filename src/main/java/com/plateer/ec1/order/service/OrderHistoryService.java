package com.plateer.ec1.order.service;

import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderHistoryService {
    public Long insertOrderHistory(OrderRequestVo orderRequest){
        log.info("------------------insertOrderHistory start");
        return 1L;
    }

    public Long updateOrderHistory(Long historyNo, OrderVo orderDto){
        log.info("------------------updateOrderHistory start");
        return 1L;
    }
}
