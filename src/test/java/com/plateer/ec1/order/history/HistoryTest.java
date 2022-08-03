package com.plateer.ec1.order.history;

import com.plateer.ec1.order.enums.history.OPT0012Type;
import com.plateer.ec1.order.service.OrderHistoryService;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HistoryTest {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Test
    @DisplayName("모니터링 로그 등록 테스트")
    void insertTest(){
        OrderRequestVo vo = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        orderHistoryService.insertOrderHistory(vo);
    }

    @Test
    @DisplayName("모니터링 로그 업데이트 테스트")
    void updateTest(){
        OrderVo vo = OrderVo.builder().build();
        vo.setProcCcd(OPT0012Type.FP);
        orderHistoryService.updateOrderHistory(1, vo);
    }

}
