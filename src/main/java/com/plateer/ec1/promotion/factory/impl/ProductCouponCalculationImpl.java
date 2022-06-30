package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.common.code.promotion.PRM0003Code;
import com.plateer.ec1.common.code.promotion.PRM0004Code;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
        return calculateMaxBenefit(calculateDcAmt);
    }

    @Override
    public PromotionType getType() {
        return PromotionType.productCoupon;
    }

    private List<ProductCouponsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------해당 상품에 적용 가능한 쿠폰 목록 조회");
        List<ProductCouponsVo> productCouponsVoList = promotionMapper.selectAvailablePromotionList(reqVO);
        return productCouponsVoList.stream()
                .filter(prd -> PRM0004Code.PRODUCT.getType().equals(prd.getCpbKindCd()))
                .distinct()
                .peek(prd -> {
                    for(ProductVo vo : reqVO.getProducts()){
                        if(Objects.equals(vo.getGoodsNo(), prd.getGoodsNo())){
                            prd.setPrdTotCnt(vo.getPrdTotCnt());
                        }
                    }
                })
                .collect(Collectors.toList());
    }


    private List<ProductCouponsVo> calculateDcAmt(List<ProductCouponsVo> productCouponsVoList){
        log.info("-------쿠폰 할인 금액 계산");
        return productCouponsVoList.stream()
                .filter(prd -> prd.getPrdPrice() > prd.getMinPurAmt())
                .peek(prd -> {
                    int dcPrice = PRM0003Code.PRICE.getType().equals(prd.getDcCcd()) ? prd.getDcVal() * prd.getPrdTotCnt() : (prd.getPrdPrice() * prd.getDcVal()) * prd.getPrdTotCnt();
                    prd.setDcPrice(Math.min(dcPrice, prd.getMaxDcAmt() * prd.getPrdTotCnt()));
                })
                .collect(Collectors.toList());
    }

    private ProductCouponResponseVo calculateMaxBenefit(List<ProductCouponsVo> productCouponsVoList){
        log.info("-------최대혜택 확인후 리턴-----------");
        productCouponsVoList.stream()
                .collect(Collectors.groupingBy(ProductCouponsVo::getGoodsNo, Collectors.maxBy(Comparator.comparing(ProductCouponsVo::getDcPrice))))
                .forEach((key, value) -> value.get().setMaxBenefitYn("Y"));

        return ProductCouponResponseVo.builder()
                .productPromotionList(productCouponsVoList)
                .memberNo(productCouponsVoList.get(0).getMbrNo())
                .build();
    }
}
