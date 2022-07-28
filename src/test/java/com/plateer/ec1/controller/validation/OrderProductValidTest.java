package com.plateer.ec1.controller.validation;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.validator.OrderProductValidators;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.JsonFileReader;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class OrderProductValidTest {

    @ToString
    public static class ListPrdGoods{
        List<PrGoodsBaseModel> prGoodsBaseModels;
    }

    @Test
    @DisplayName("존재하는 상품인지 확인")
    void isExistProductTest(){
        // 1. 상품이 아예 없을때
        ListPrdGoods listPrdGoods = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest1.json", ListPrdGoods.class);

        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods.prGoodsBaseModels)
                .build();
        Assertions.assertFalse(OrderProductValidators.isExistProduct.test(vo));

        // 2. 구매하려는 상품의 개수와 조회된 상품의 개수가 다를때 + 상품이 하나 이상이긴 함
        ListPrdGoods listPrdGoods2 = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest2.json", ListPrdGoods.class);
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods2.prGoodsBaseModels)
                .build();
        Assertions.assertFalse(OrderProductValidators.isExistProduct.test(vo2));

        // 3. 1,2 둘다 충족하는 경우
        ListPrdGoods listPrdGoods3 = JsonFileReader.getObject("json/order/valid/productValid/isExistProductTest2.json", ListPrdGoods.class);
        OrderValidationVo vo3 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods3.prGoodsBaseModels)
                .build();
        Assertions.assertTrue(OrderProductValidators.isExistProduct.test(vo3));
    }

    @Test
    @DisplayName("판매중인 상품인지 확인")
    void isSellingProductTest(){
        // 1. 판매중이지 x
        ListPrdGoods prd1 = JsonFileReader.getObject("json/order/valid/productValid/isSellingProductTest1.json", ListPrdGoods.class);
        // todo :  why Null?
        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(prd1.prGoodsBaseModels)
                .build();
        Assertions.assertFalse(OrderProductValidators.isSellingProduct.test(vo));

        // 2. 판매중임 o
        ListPrdGoods prd2 = JsonFileReader.getObject("json/order/valid/productValid/isSellingProductTest2.json", ListPrdGoods.class);
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(prd2.prGoodsBaseModels)
                .build();
        Assertions.assertTrue(OrderProductValidators.isSellingProduct.test(vo2));
    }

}
