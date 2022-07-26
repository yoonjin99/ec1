package com.plateer.ec1.controller.history.dao;

import com.plateer.ec1.order.mapper.history.OrderHistoryTrxMapper;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.history.OrderHistoryInsertVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class HistoryDaoTest {

    @Autowired
    private OrderHistoryTrxMapper mapper;

    @Test
    void insertTest(){
        OrderHistoryInsertVo vo = OrderHistoryInsertVo.builder().build();
        mapper.insertHistoryLog(vo);
    }
}
