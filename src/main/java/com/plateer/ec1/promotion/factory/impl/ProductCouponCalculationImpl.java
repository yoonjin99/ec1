package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.common.code.promotion.PRM0004Code;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.vo.*;
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
        List<ProductCouponsVo> productCouponsVoList = promotionMapper.selectAvailablePromotionList(reqVO);
        productCouponsVoList =  productCouponsVoList.stream()
                                                    .filter(prd -> PRM0004Code.PRODUCT.getType().equals(prd.getCpnKindCd()) && prd.getPrdPrice() >= prd.getMinPurAmt())
                                                    .distinct()
                                                    .collect(Collectors.toList());
        productCouponsVoList.forEach(productCouponsVo -> productCouponsVo.setterPrdTotCnt(reqVO));
        return productCouponsVoList;
    }

    private List<ProductCouponsVo> calculateDcAmt(List<ProductCouponsVo> productCouponsVoList){
        log.info("-------쿠폰 할인 금액 계산");
        productCouponsVoList.forEach(productCouponsVo -> productCouponsVo.calculatePrice(productCouponsVo));
        return productCouponsVoList;
    }

    private ProductCouponResponseVo calculateMaxBenefit(List<ProductCouponsVo> productCouponsVoList, String mbrNo){
        log.info("-------최대혜택 확인후 리턴-----------");
        productCouponsVoList.stream()
                            .collect(Collectors.groupingByConcurrent(ProductCouponsVo::getGoodsNo,
                                    Collectors.maxBy(Comparator.comparing(ProductCouponsVo::getDcPrice))))
                            .forEach((s, productCouponsVo) -> {
                                productCouponsVo.ifPresent(couponsVo -> couponsVo.setMaxBenefitYn("Y"));
                            });

        return ProductCouponResponseVo.builder()
                .productPromotionList(productCouponsVoList)
                .memberNo(mbrNo)
                .build();
    }
}
