package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.common.code.promotion.PRM0003Code;
import com.plateer.ec1.common.code.promotion.PRM0004Code;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.vo.*;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCouponCalculationImpl implements Calculation {

    private final PromotionMapper promotionMapper;

    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        List<ProductCouponsVo> availablePromotionData = getAvailablePromotionData(vo);
        List<ProductCouponsVo> calculateDcAmt = calculateDcAmt(availablePromotionData);
        return calculateMaxBenefit(calculateDcAmt, vo.getMbrNo());
    }

    @Override
    public PromotionType getType() {
        return PromotionType.productCoupon;
    }

    private List<ProductCouponsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------해당 상품에 적용 가능한 쿠폰 목록 조회");
        List<PromotionVo> promotionList = promotionMapper.selectAvailablePromotionList(reqVO); // 프로모션 목록 가져옴

        List<ProductCouponsVo> productCouponsVoList = new ArrayList<>();
        for(ProductVo prd : reqVO.getProducts()){
            ProductCouponsVo couponsVo = new ProductCouponsVo();
            List<PromotionVo> prm = promotionList.stream()
                    .filter(vo -> vo.getGoodsNo().equals(prd.getGoodsNo()) &&  vo.getItemNo().equals(prd.getItemNo()) && vo.getMinPurAmt() <= prd.getPrdPrice())
                    .collect(Collectors.toList());
            prm.stream().filter(p -> p.getCpnIssNo().equals(prd.getCpnIssNo()) && p.getItemNo().equals(prd.getItemNo())).forEach(t -> t.setApplyCpnYn("Y"));

            couponsVo.setProductVo(prd);
            couponsVo.setPromotionVoList(prm);
            productCouponsVoList.add(couponsVo);
        }
        return productCouponsVoList;
    }

    private List<ProductCouponsVo> calculateDcAmt(List<ProductCouponsVo> productCouponsVoList){
        log.info("-------쿠폰 할인 금액 계산");
        productCouponsVoList.forEach(productCouponsVo -> productCouponsVo.calculatePrice(productCouponsVo));
        return productCouponsVoList;
    }

    private ProductCouponResponseVo calculateMaxBenefit(List<ProductCouponsVo> productCouponsVoList, String mbrNo){
        log.info("-------최대혜택 확인후 리턴-----------");
        List<PromotionVo> map = new ArrayList<>();
        for (ProductCouponsVo productCouponsVo : productCouponsVoList) {
            List<PromotionVo> finalMap = map;
            productCouponsVo.getPromotionVoList().stream()
                    .filter(v -> finalMap.stream().noneMatch(m -> v.getPrmNo().equals(m.getPrmNo()) && v.getCpnIssNo().equals(m.getCpnIssNo())))
                    .max(Comparator.comparing(PromotionVo::getDcPrice))
                    .ifPresent(promotionVo -> promotionVo.setMaxBenefitYn("Y"));

            PromotionVo useCoupon = new PromotionVo();
            for(PromotionVo vo : productCouponsVo.getPromotionVoList()){
                if("Y".equals(vo.getMaxBenefitYn())){
                    useCoupon = vo;
                }
            }
            map.add(useCoupon);
        }

        return ProductCouponResponseVo.builder()
                .productPromotionList(productCouponsVoList)
                .memberNo(mbrNo)
                .build();
    }
}
