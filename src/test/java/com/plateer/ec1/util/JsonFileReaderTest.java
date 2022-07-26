package com.plateer.ec1.util;

import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class JsonFileReaderTest {

    @Test
    @DisplayName("주문 json 파일 읽기 테스트")
    void orderJsonReader(){
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        log.info(orderRequestVo.toString() + "json 파일 읽기 테스트");
    }
}
