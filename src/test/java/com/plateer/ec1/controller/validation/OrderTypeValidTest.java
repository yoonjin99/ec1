package com.plateer.ec1.controller.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.validator.OrderTypeValidators;
import com.plateer.ec1.order.vo.OrderRequestVo;
import com.plateer.ec1.order.vo.OrderValidationVo;
import com.plateer.ec1.util.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTypeValidTest {

    @Test
    @DisplayName("ecoupon 상품이 맞는지 확인")
    void isEcouponProductTest(){
        // 1. ecoupon 상품이 아님
        List<PrGoodsBaseModel> listPrdGoods = JsonFileReader.getObject("json/order/generalProduct.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponProduct.test(vo));

        // 2. ecoupon 상품이 맞음
        List<PrGoodsBaseModel> listPrdGoods2 = JsonFileReader.getObject("json/order/mobileProduct.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods2)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isEcouponProduct.test(vo2));
    }

    @Test
    @DisplayName("ecoupon 필수 데이터 확인")
    void isEcouponValidTest(){
        // 휴대폰 번호가 없음
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/valid/typeValid/ecoupon/noPhoneNum.json", OrderRequestVo.class);

        OrderValidationVo vo = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponValid.test(vo));

        // 휴대폰 번호가 있음
        OrderRequestVo orderRequestVo2 = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);

        OrderValidationVo vo2 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo2)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isEcouponValid.test(vo2));
    }

    @Test
    @DisplayName("배송지가 1개 or 배송지수 = 상품개수 확인")
    void isEcouponOrdDvpValidTest(){
        // 1. 배송지가 1개일때
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/valid/typeValid/ecoupon/oneDvp.json", OrderRequestVo.class);
        OrderValidationVo vo = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isEcouponOrdDvpValid.test(vo));

        // 2. 배송지랑 상품 개수랑 다름
        OrderRequestVo orderRequestVo2 = JsonFileReader.getObject("json/order/valid/typeValid/ecoupon/diffGoodsNum.json", OrderRequestVo.class);
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo2)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isEcouponOrdDvpValid.test(vo2));

        // 3. 배송지랑 상품개수랑 같음
        OrderRequestVo orderRequestVo3 = JsonFileReader.getObject("json/order/valid/typeValid/ecoupon/sameDvp.json", OrderRequestVo.class);
        OrderValidationVo vo3 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo3)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isEcouponOrdDvpValid.test(vo3));
    }

    @Test
    @DisplayName("일반상품이고 직배송인지 확인")
    void isGeneralProductTest(){
        // 1. 일반상품이 아님
        List<PrGoodsBaseModel> listPrdGoods = JsonFileReader.getObject("json/order/mobileProduct.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isGeneralProduct.test(vo));

        // 2. 직배송이 아님
        List<PrGoodsBaseModel> listPrdGoods2 = JsonFileReader.getObject("json/order/mobileProduct.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo2 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods2)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isGeneralProduct.test(vo2));

        // 3. 1,2 해당함
        List<PrGoodsBaseModel> listPrdGoods3 = JsonFileReader.getObject("json/order/generalProduct.json", new TypeReference<List<PrGoodsBaseModel>>(){});

        OrderValidationVo vo3 = OrderValidationVo.builder()
                .prGoodsBaseModel(listPrdGoods3)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isGeneralProduct.test(vo3));
    }

    @Test
    @DisplayName("일반주문 필수 데이터 확인")
    void isGeneralValidTest(){
        // 1. 수취인주소 없음
        OrderRequestVo orderRequestVo = JsonFileReader.getObject("json/order/valid/typeValid/general/noAddr.json", OrderRequestVo.class);
        OrderValidationVo vo = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isGeneralValid.test(vo));

        // 2. 휴대폰번호 없음
        OrderRequestVo orderRequestVo2 = JsonFileReader.getObject("json/order/valid/typeValid/general/noPhoneNum.json", OrderRequestVo.class);
        OrderValidationVo vo2 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo2)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isGeneralValid.test(vo2));

        // 3. 수취인명 없음
        OrderRequestVo orderRequestVo3 = JsonFileReader.getObject("json/order/valid/typeValid/general/noName.json", OrderRequestVo.class);
        OrderValidationVo vo3 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo3)
                .build();
        Assertions.assertFalse(OrderTypeValidators.isGeneralValid.test(vo3));

        // 4. 1,2,3 충족함
        OrderRequestVo orderRequestVo4 = JsonFileReader.getObject("json/order/orderRequest.json", OrderRequestVo.class);
        OrderValidationVo vo4 = OrderValidationVo.builder()
                .orderRequestVo(orderRequestVo4)
                .build();
        Assertions.assertTrue(OrderTypeValidators.isGeneralValid.test(vo4));
    }
}
