package com.plateer.ec1.promotion;

import com.plateer.ec1.promotion.service.PromotionService;
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
public class ProductCouponTest {

    @Autowired
    PromotionService promotionService;

    @Test
    @DisplayName("상품쿠폰적용테스트1")
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
        log.info(pcsList.toString() + "결과값");
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
    @DisplayName("상품쿠폰적용테스트2")
    void prd_cpn_apply_test2(){
        // 객체 생성
        ProductVo goodsP001_1 = new ProductVo();
        ProductVo goodsP001_2 = new ProductVo();
        ProductVo goodsP002_1 = new ProductVo();
        ProductVo goodsP002_2 = new ProductVo();
        ProductVo goodsP005_1 = new ProductVo();
        ProductVo goodsP005_2 = new ProductVo();
        ProductVo goodsP005_3 = new ProductVo();
        ProductVo goodsP006_0 = new ProductVo();
        ProductVo goodsP007_1 = new ProductVo();
        ProductVo goodsP007_2 = new ProductVo();
        ProductVo goodsP007_3 = new ProductVo();

        // 객체 셋팅
        goodsP001_1.setGoodsNo("P001");
        goodsP001_1.setPrdPrice(29000);
        goodsP001_1.setItemNo("1");

        goodsP001_2.setGoodsNo("P001");
        goodsP001_2.setPrdPrice(29000);
        goodsP001_2.setItemNo("2");

        goodsP002_1.setGoodsNo("P002");
        goodsP002_1.setPrdPrice(10250);
        goodsP002_1.setItemNo("1");

        goodsP002_2.setGoodsNo("P002");
        goodsP002_2.setPrdPrice(10250);
        goodsP002_2.setItemNo("2");

        goodsP005_1.setGoodsNo("P005");
        goodsP005_1.setPrdPrice(9000);
        goodsP005_1.setItemNo("1");

        goodsP005_2.setGoodsNo("P005");
        goodsP005_2.setPrdPrice(9000);
        goodsP005_2.setItemNo("2");

        goodsP005_3.setGoodsNo("P005");
        goodsP005_3.setPrdPrice(9000);
        goodsP005_3.setItemNo("3");

        goodsP006_0.setGoodsNo("P006");
        goodsP006_0.setPrdPrice(140000);
        goodsP006_0.setItemNo("0");

        goodsP007_1.setGoodsNo("P007");
        goodsP007_1.setPrdPrice(24000);
        goodsP007_1.setItemNo("1");

        goodsP007_2.setGoodsNo("P007");
        goodsP007_2.setPrdPrice(24000);
        goodsP007_2.setItemNo("2");

        goodsP007_3.setGoodsNo("P007");
        goodsP007_3.setPrdPrice(24000);
        goodsP007_3.setItemNo("3");


        List goodsList = new ArrayList();
        goodsList.add(goodsP001_1);
        goodsList.add(goodsP001_2);
        goodsList.add(goodsP002_1);
        goodsList.add(goodsP002_2);
        goodsList.add(goodsP005_1);
        goodsList.add(goodsP005_2);
        goodsList.add(goodsP005_3);
        goodsList.add(goodsP006_0);
        goodsList.add(goodsP007_1);
        goodsList.add(goodsP007_2);
        goodsList.add(goodsP007_3);


        PromotionRequestVo reqVo = new PromotionRequestVo();
        reqVo.setMbrNo("test02");
        reqVo.setProducts(goodsList);
        ProductCouponResponseVo pcsList = promotionService.getProductCouponApplyData(reqVo);
        log.info(pcsList.toString() + "결과값");
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

//        Assertions.assertThat(list)
//                .filteredOn(productCouponsVo -> productCouponsVo.getProductVo().getGoodsNo().equals("P001") && productCouponsVo.getProductVo().getItemNo().equals("1"))
//                .extracting( "promotionVoList")
//                .containsOnly(prmList);
        Assertions.assertThat(list.size()).isEqualTo(11);
    }

    @Test
    @DisplayName("상품쿠폰적용테스트3")
    void prd_cpn_apply_test3(){
        ProductVo goodsP001 = new ProductVo();
        ProductVo goodsP002 = new ProductVo();
        ProductVo goodsP005 = new ProductVo();
        ProductVo goodsP006 = new ProductVo();
        ProductVo goodsP007 = new ProductVo();

        goodsP001.setGoodsNo("P001");
        goodsP001.setPrdPrice(29000);
        goodsP001.setItemNo("1");

        goodsP002.setGoodsNo("P002");
        goodsP002.setPrdPrice(10250);
        goodsP002.setItemNo("1");

        goodsP005.setGoodsNo("P005");
        goodsP005.setPrdPrice(9000);
        goodsP005.setItemNo("1");

        goodsP006.setGoodsNo("P006");
        goodsP006.setPrdPrice(140000);
        goodsP006.setItemNo("0");

        goodsP007.setGoodsNo("P007");
        goodsP007.setPrdPrice(24000);
        goodsP007.setItemNo("1");

        List goodsList = new ArrayList();
        goodsList.add(goodsP001);
        goodsList.add(goodsP002);
        goodsList.add(goodsP005);
        goodsList.add(goodsP006);
        goodsList.add(goodsP007);

        PromotionRequestVo reqVo = new PromotionRequestVo();
        reqVo.setMbrNo("test03");
        reqVo.setProducts(goodsList);
        ProductCouponResponseVo pcsList = promotionService.getProductCouponApplyData(reqVo);
        log.info(pcsList.toString() + "결과값");
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

        Assertions.assertThat(list.size()).isEqualTo(8);

//        Assertions.assertThat(list)
//                .filteredOn(productCouponsVo -> productCouponsVo.getProductVo().getGoodsNo().equals("P001") && productCouponsVo.getProductVo().getItemNo().equals("1"))
//                .extracting( "promotionVoList")
//                .containsOnly(prmList);
    }

}
