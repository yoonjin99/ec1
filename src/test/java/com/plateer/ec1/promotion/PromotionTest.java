package com.plateer.ec1.promotion;

import com.plateer.ec1.promotion.service.PromotionService;
import com.plateer.ec1.promotion.service.coupon.DownloadCouponService;
import com.plateer.ec1.promotion.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("상품쿠폰적용테스트")
    void prd_cpn_apply_test(){
        ProductVo goodsP001 = new ProductVo();
        ProductVo goodsP002 = new ProductVo();
        goodsP001.setGoodsNo("P001");
        goodsP001.setPrdPrice(29000);
        goodsP001.setItemNo("1");
        goodsP002.setGoodsNo("P002");
        goodsP002.setPrdPrice(10250);
        goodsP002.setItemNo("1");
        List goodsList = new ArrayList();
        goodsList.add(goodsP001);
        goodsList.add(goodsP002);
        PromotionRequestVo reqVo = new PromotionRequestVo();
        reqVo.setMbrNo("test01");
        reqVo.setProducts(goodsList);
        ProductCouponResponseVo pcsList = promotionService.getProductCouponApplyData(reqVo);
        List<ProductCouponsVo> list = pcsList.getProductPromotionList();

        // 예상 결과값
        List<PromotionVo> prmList = new ArrayList<>();
        PromotionVo prm = new PromotionVo();
        prm.setGoodsNo("P001");
        prm.setItemNo("1");
        prm.setPrmNo(1L);
        prm.setDcPrice(1000);
        prm.setMaxBenefitYn("Y");
        prm.setDcVal(1000);
        prm.setMinPurAmt(500);
        prm.setMaxDcAmt(1000);
        prm.setCpnIssNo(16L);
        prm.setDcCcd("10");
        prm.setCpnKindCd("10");
        prmList.add(prm);

        Assertions.assertThat(list)
                .filteredOn(productCouponsVo -> productCouponsVo.getProductVo().getGoodsNo().equals("P001") && productCouponsVo.getProductVo().getItemNo().equals("1"))
                .extracting( "promotionVoList")
                .containsOnly(prmList);
    }



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
        productVo2.setItemNo("2");

        List<ProductVo> list = new ArrayList<>();
        list.add(productVo);
        list.add(productVo2);

        requestPromotionVO.setProducts(list);
        ProductCouponResponseVo vo = promotionService.getProductCouponApplyData(requestPromotionVO);
        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
    }

    @Test
    void cartCoupon1(){
        log.info("주문1 장바구니 쿠폰 테스트-----------");
        PromotionRequestVo requestPromotionVO = new PromotionRequestVo();
        requestPromotionVO.setMbrNo("test01");
        ProductVo productVo = new ProductVo();
        productVo.setGoodsNo("P001");
        productVo.setItemNo("1");
        productVo.setOrderCnt(2);
        productVo.setPrdPrice(28000);

        ProductVo productVo2 = new ProductVo();
        productVo2.setGoodsNo("P002");
        productVo2.setItemNo("1");
        productVo2.setOrderCnt(3);
        productVo2.setPrdPrice(10250);

        List<ProductVo> list = new ArrayList<>();
        list.add(productVo);
        list.add(productVo2);

        requestPromotionVO.setProducts(list);
        CartCouponResponseVo vo = promotionService.getCartCouponApplyData(requestPromotionVO);
        log.info(vo.toString() + "결과!!!!!!!!!!!!!!");
    }

    @Test
    void setDownloadAvailableCouponService(){
        log.info("쿠폰 다운로드");
        CouponRequestVo vo = new CouponRequestVo();
        vo.setPrmNo(7L);
        vo.setMbrNo("test03");
        downloadCouponService.downloadCoupon(vo);
    }

    @Test
    void couponCancelTest(){

    }
}
