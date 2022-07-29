package com.plateer.ec1.controller.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.validator.OrderProductValidators;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.JsonFileReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class OrderProductValidTest {

    @Test
    @DisplayName("존재하는 상품인지 확인")
    void isExistProductTest(){
        // 1. 상품이 아예 없을때
        List<PrGoodsBaseModel> listPrdGoods = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest1.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods)
                .build();
        Assertions.assertFalse(OrderProductValidators.isExistProduct.test(vo));

        // 2. 구매하려는 상품의 개수와 조회된 상품의 개수가 다를때 + 상품이 하나 이상이긴 함
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        List<PrGoodsBaseModel> listPrdGoods2 = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest2.json", new TypeReference<List<PrGoodsBaseModel>>(){});
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods2)
                .orderRequestVo(orderRequestVo)
                .build();
        Assertions.assertFalse(OrderProductValidators.isExistProduct.test(vo2));

        // 3. 1,2 둘다 충족하는 경우
        OrderRequestVo orderRequestVo2 = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        List<PrGoodsBaseModel> listPrdGoods3 = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest2.json", new TypeReference<List<PrGoodsBaseModel>>(){});
        OrderValidationVo vo3 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods3)
                .orderRequestVo(orderRequestVo2)
                .build();
        Assertions.assertTrue(OrderProductValidators.isExistProduct.test(vo3));
    }

    @Test
    @DisplayName("판매중인 상품인지 확인")
    void isSellingProductTest(){
        // 1. 판매중이지 x
        List<PrGoodsBaseModel> prd1 = JsonFileReader.getObject("json/order/valid/productValid/isSellingProductTest1.json", new TypeReference<List<PrGoodsBaseModel>>(){});
        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(prd1)
                .build();
        Assertions.assertFalse(OrderProductValidators.isSellingProduct.test(vo));

        // 2. 판매중임 o
        List<PrGoodsBaseModel> prd2 = JsonFileReader.getObject("json/order/valid/productValid/isSellingProductTest2.json", new TypeReference<List<PrGoodsBaseModel>>(){});
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(prd2)
                .build();
        Assertions.assertTrue(OrderProductValidators.isSellingProduct.test(vo2));
    }

}
