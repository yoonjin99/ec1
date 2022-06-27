package com.plateer.ec1.promotion;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.service.coupon.DownloadAvailableCouponService;
import com.plateer.ec1.promotion.service.coupon.DownloadCouponService;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import com.plateer.ec1.promotion.vo.PromotionVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PromotionTest {

    @Autowired
    PromotionService promotionService;

    @Autowired
    DownloadAvailableCouponService downloadAvailableCouponService;

    @Autowired
    DownloadCouponService downloadCouponService;

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

    @Test
    void availableCouponList(){
        log.info("다운 가능한 쿠폰 리스트 조회");
        downloadAvailableCouponService.getDownloadAvailableCouponList("test01");
    }

    @Test
    void setDownloadAvailableCouponService(){
        log.info("다운 가능한 쿠폰 리스트 조회");
        PromotionVo vo = new PromotionVo();
        vo.setPrmNo(1L);
        downloadCouponService.downloadCoupon("test01", vo);
    }
}
