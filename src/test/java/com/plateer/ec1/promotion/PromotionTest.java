package com.plateer.ec1.promotion;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.service.coupon.DownloadCouponService;
import com.plateer.ec1.promotion.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class PromotionTest {

    @Autowired
    PromotionService promotionService;


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
        requestPromotionVO.setMbrNo("test01");
        ProductVo productVo = new ProductVo();
        productVo.setPrdTotCnt(3);
        productVo.setGoodsNo("P001");
        ProductVo productVo2 = new ProductVo();
        productVo2.setPrdTotCnt(5);
        productVo2.setGoodsNo("P002");

        List<ProductVo> list = new ArrayList<>();
        list.add(productVo);
        list.add(productVo2);

        requestPromotionVO.setProducts(list);
        ProductCouponResponseVo vo = promotionService.getProductCouponApplyData(requestPromotionVO);
        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
    }

    @Test
    void cartCoupon(){
        log.info("장바구니쿠폰 금액 계산");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        promotionService.getCartCouponApplyData(requestPromotionVO);
    }

    @Test
    void setDownloadAvailableCouponService(){
        log.info("다운 가능한 쿠폰 리스트 조회");
        CouponRequestVo vo = new CouponRequestVo();
        vo.setPrmNo(1L);
        vo.setMbrNo("test01");
        downloadCouponService.downloadCoupon(vo);
    }

    @Test
    void couponCancelTest(){

    }
}
