package com.plateer.ec1.order.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.service.OrderContext;
import com.plateer.ec1.order.strategy.after.impl.FoAfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.strategy.data.impl.EcouponDataStrategy;
import com.plateer.ec1.order.strategy.data.impl.GeneralDataStrategy;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class OrderDataTest {

    @Autowired
    OrderContext context;


    @Test
    @DisplayName("일반주문 테스트 가상계좌 주문")
    void generalVrAc(){
        OrderRequestVo orderRequest = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        context.execute(new GeneralDataStrategy(), new FoAfterStrategy(), orderRequest);
    }

    @Test
    @DisplayName("이쿠폰 테스트 가상계좌 주문")
    void ecouponVrAc(){
        OrderRequestVo orderRequest = JsonFileReader.getObject("json/order/orderRequestEcoupon.json", OrderRequestVo.class);
        context.execute(new EcouponDataStrategy(), new FoAfterStrategy(), orderRequest);
    }

    @Test
    @DisplayName("일반주문 테스트 포인트 주문")
    void generalP(){
        OrderRequestVo orderRequest = JsonFileReader.getObject("json/order/orderRequestGeneralP.json", OrderRequestVo.class);
        context.execute(new GeneralDataStrategy(), new FoAfterStrategy(), orderRequest);
    }

    @Test
    @DisplayName("이쿠폰 테스트 포인트 주문")
    void ecouponP(){
        OrderRequestVo orderRequest = JsonFileReader.getObject("json/order/orderRequestEcouponP.json", OrderRequestVo.class);
        context.execute(new EcouponDataStrategy(), new FoAfterStrategy(), orderRequest);
    }
}
