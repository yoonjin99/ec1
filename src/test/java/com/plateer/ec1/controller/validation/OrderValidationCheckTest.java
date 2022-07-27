package com.plateer.ec1.controller.validation;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderValidationCheckTest {

    @Test
    @DisplayName("유효성 검즘")
    void validationCheck(){
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        ListPrdGoods prGoodsBaseModel = JsonFileReader.getObject("json/order/prGoodsBase.json", ListPrdGoods.class);

        OrderValidationVo validationDto = OrderValidationVo.createVo(orderRequestVo, prGoodsBaseModel.prGoodsBaseModels);
        boolean result = OrderType.get(orderRequestVo).test(validationDto);
        Assertions.assertTrue(result);
    }

    public static class ListPrdGoods{
        List<PrGoodsBaseModel> prGoodsBaseModels;
    }
}
