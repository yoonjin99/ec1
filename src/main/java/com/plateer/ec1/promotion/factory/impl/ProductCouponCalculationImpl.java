package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.common.code.promotion.PRM0003Code;
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
        List<ProductCouponsVo> productCouponsVoList = promotionMapper.selectProductCoupon(reqVO);
        Loop1 :
        for(ProductCouponsVo cpn : productCouponsVoList){
            for(ProductVo vo : reqVO.getProducts()){
                if(!vo.getGoodsNo().equals(cpn.getProductVo().getGoodsNo()) && !vo.getItemNo().equals(cpn.getProductVo().getItemNo())){
                    productCouponsVoList.remove(cpn);
                }
                break Loop1;
            }
        }

        for (ProductCouponsVo cpn : productCouponsVoList) {
            cpn.getPromotionVoList().stream()
                    .filter(vo -> reqVO.getProducts().stream().anyMatch(p -> vo.getPrmNo().equals(p.getPrmNo()) && vo.getCpnIssNo().equals(p.getCpnIssNo())))
                    .forEach(t -> t.setApplyCpnYn("Y"));
        }
        return productCouponsVoList;
    }

    private List<ProductCouponsVo> calculateDcAmt(List<ProductCouponsVo> productCouponsVoList){
        log.info("-------쿠폰 할인 금액 계산");
        productCouponsVoList.forEach(productCouponsVo -> productCouponsVo.calculatePrice(productCouponsVo));
        productCouponsVoList.forEach(productCouponsVo -> productCouponsVo.getPromotionVoList().removeIf(prm -> prm.getMinPurAmt() > productCouponsVo.getProductVo().getPrdPrice()));
        return productCouponsVoList;
    }

    private ProductCouponResponseVo calculateMaxBenefit(List<ProductCouponsVo> productCouponsVoList, String mbrNo){
        log.info("-------최대혜택 확인후 리턴-----------");
        List<PromotionVo> map = new ArrayList<>();
        for (ProductCouponsVo productCouponsVo : productCouponsVoList) {
            productCouponsVo.getPromotionVoList().stream()
                    .filter(v -> map.stream().noneMatch(m -> v.getPrmNo().equals(m.getPrmNo()) && v.getCpnIssNo().equals(m.getCpnIssNo())))
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
