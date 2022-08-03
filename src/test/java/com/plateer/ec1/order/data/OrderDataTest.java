package com.plateer.ec1.order.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.service.OrderContext;
import com.plateer.ec1.order.strategy.after.impl.FoAfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.strategy.data.impl.GeneralDataStrategy;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderDataTest {

    @Autowired
    OrderContext context;


    @Test
    @DisplayName("일반주문 데이터 생성 테스트")
    void dataCreate(){
        OrderRequestVo orderRequest = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        context.execute(new GeneralDataStrategy(), new FoAfterStrategy(), orderRequest);
    }

    @Test
    @DisplayName("생성된 데이터로 결제 테스트")
    void payment(){

    }

    @Test
    @DisplayName("데이터 등록 테스트")
    void insertData(){

    }
}
