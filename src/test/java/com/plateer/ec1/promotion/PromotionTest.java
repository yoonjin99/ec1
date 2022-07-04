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
    void productCoupon1(){
        log.info("주문1 테스트(상품쿠폰 P001만 해당)----------");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        requestPromotionVO.setMbrNo("test01");
        ProductVo productVo = new ProductVo();
        productVo.setPrdPrice(15000);
        productVo.setGoodsNo("P001");
        productVo.setItemNo("1");
        productVo.setPrmNo(1L);
        productVo.setCpnIssNo(1L);

        ProductVo productVo2 = new ProductVo();
        productVo2.setPrdPrice(8000);
        productVo2.setGoodsNo("P001");
        productVo2.setItemNo("3");

        List<ProductVo> list = new ArrayList<>();
        list.add(productVo);
        list.add(productVo2);

        requestPromotionVO.setProducts(list);
        ProductCouponResponseVo vo = promotionService.getProductCouponApplyData(requestPromotionVO);
        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
    }

//    @Test
//    void productCoupon2(){
//        log.info("주문2 테스트 상품쿠폰(3개다)----------");
//        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
//        requestPromotionVO.setMbrNo("test01");
//
//        ProductVo productVo = new ProductVo();
//        productVo.setPrdTotCnt(1);
//        productVo.setGoodsNo("P002");
//
//        ProductVo productVo2 = new ProductVo();
//        productVo2.setPrdTotCnt(1);
//        productVo2.setGoodsNo("P005");
//
//        ProductVo productVo3 = new ProductVo();
//        productVo3.setPrdTotCnt(1);
//        productVo3.setGoodsNo("P006");
//
//        List<ProductVo> list = new ArrayList<>();
//        list.add(productVo);
//        list.add(productVo2);
//        list.add(productVo3);
//
//        requestPromotionVO.setProducts(list);
//        ProductCouponResponseVo vo = promotionService.getProductCouponApplyData(requestPromotionVO);
//        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
//    }
//
//    @Test
//    void productCoupon3(){
//        log.info("주문3 테스트----------");
//        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
//        requestPromotionVO.setMbrNo("test01");
//
//        ProductVo productVo = new ProductVo();
//        productVo.setPrdTotCnt(1);
//        productVo.setGoodsNo("P001");
//
//        ProductVo productVo2 = new ProductVo();
//        productVo2.setPrdTotCnt(1);
//        productVo2.setGoodsNo("P002");
//
//        ProductVo productVo3 = new ProductVo();
//        productVo3.setPrdTotCnt(1);
//        productVo3.setGoodsNo("P005");
//
//        ProductVo productVo4 = new ProductVo();
//        productVo4.setPrdTotCnt(1);
//        productVo4.setGoodsNo("P006");
//
//        ProductVo productVo5 = new ProductVo();
//        productVo5.setPrdTotCnt(1);
//        productVo5.setGoodsNo("P007");
//
//        List<ProductVo> list = new ArrayList<>();
//        list.add(productVo);
//        list.add(productVo2);
//        list.add(productVo3);
//        list.add(productVo4);
//        list.add(productVo5);
//
//        requestPromotionVO.setProducts(list);
//        ProductCouponResponseVo vo = promotionService.getProductCouponApplyData(requestPromotionVO);
//        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
//    }
//
//    @Test
//    void cartCoupon1(){
//        log.info("주문1 장바구니 쿠폰 테스트-----------");
//        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
//        requestPromotionVO.setMbrNo("test01");
//        ProductVo productVo = new ProductVo();
//        productVo.setPrdTotCnt(2);
//        productVo.setGoodsNo("P001");
//        productVo.setPrdDcPrice(56000);
//
//        ProductVo productVo2 = new ProductVo();
//        productVo2.setPrdTotCnt(3);
//        productVo2.setGoodsNo("P002");
//        productVo2.setPrdDcPrice(30750);
//
//        List<ProductVo> list = new ArrayList<>();
//        list.add(productVo);
//        list.add(productVo2);
//
//        requestPromotionVO.setProducts(list);
//        CartCouponResponseVo vo = promotionService.getCartCouponApplyData(requestPromotionVO);
//        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
//    }
//
//    @Test
//    void cartCoupon3(){
//        log.info("주문3 장바구니 쿠폰 테스트-----------");
//        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
//        requestPromotionVO.setMbrNo("test01");
//
//        ProductVo productVo = new ProductVo();
//        productVo.setPrdTotCnt(1);
//        productVo.setGoodsNo("P001");
//        productVo.setPrdDcPrice(28000);
//
//        ProductVo productVo2 = new ProductVo();
//        productVo2.setPrdTotCnt(1);
//        productVo2.setGoodsNo("P002");
//        productVo2.setPrdDcPrice(9250);
//
//        ProductVo productVo3 = new ProductVo();
//        productVo3.setPrdTotCnt(1);
//        productVo3.setGoodsNo("P005");
//        productVo3.setPrdDcPrice(8000);
//
//        ProductVo productVo4 = new ProductVo();
//        productVo4.setPrdTotCnt(1);
//        productVo4.setGoodsNo("P006");
//        productVo4.setPrdDcPrice(137000);
//
//        ProductVo productVo5 = new ProductVo();
//        productVo5.setPrdTotCnt(1);
//        productVo5.setGoodsNo("P007");
//        productVo5.setPrdDcPrice(24000);
//
//        List<ProductVo> list = new ArrayList<>();
//        list.add(productVo);
//        list.add(productVo2);
//        list.add(productVo3);
//        list.add(productVo4);
//        list.add(productVo5);
//
//        requestPromotionVO.setProducts(list);
//        CartCouponResponseVo vo = promotionService.getCartCouponApplyData(requestPromotionVO);
//        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
//    }

    @Test
    void setDownloadAvailableCouponService(){
        log.info("다운 가능한 쿠폰 리스트 조회");
        CouponRequestVo vo = new CouponRequestVo();
        vo.setPrmNo(10L);
        vo.setMbrNo("test01");
        downloadCouponService.downloadCoupon(vo);
    }

    @Test
    void couponCancelTest(){

    }
}
