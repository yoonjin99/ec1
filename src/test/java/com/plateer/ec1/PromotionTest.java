package com.plateer.ec1;

import com.plateer.ec1.promotion.service.impl.PromotionServiceImpl;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PromotionTest {

    @Autowired
    PromotionServiceImpl promotionService;

    @Test
    void priceDiscount(){
        log.info("가격조정할인 금액 계산");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        promotionService.getPriceDiscountApplyData(requestPromotionVO);
    }


    @Test
    void productCoupon(){
        log.info("상품쿠폰 금액 계산");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        promotionService.getProductCouponApplyData(requestPromotionVO);
    }

    @Test
    void cartCoupon(){
        log.info("장바구니쿠폰 금액 계산");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        promotionService.getCartCouponApplyData(requestPromotionVO);
    }
}
