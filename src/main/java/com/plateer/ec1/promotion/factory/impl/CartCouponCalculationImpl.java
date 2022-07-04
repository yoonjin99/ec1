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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartCouponCalculationImpl implements Calculation {

    private final PromotionMapper promotionMapper;

    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        log.info("-------CartCouponCalculation getCalculationData start");
        List<ProductCouponsVo> getAvailablePromotionDataList = getAvailablePromotionData(vo);
        List<ProductCouponsVo> calculateDcAmtList =  calculateDcAmt(getAvailablePromotionDataList,vo);
        CartCouponResponseVo cartCouponResponseVo = calculateMaxBenefit(calculateDcAmtList, vo.getMbrNo());
        return cartCouponResponseVo;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.cartCoupon;
    }

    private List<ProductCouponsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------적용 가능한 장바구니 쿠폰 목록 가져옴");
//        List<ProductCouponsVo> productCouponsVoList = promotionMapper.selectAvailablePromotionList(reqVO);
//        productCouponsVoList =  productCouponsVoList.stream()
//                .filter(prd -> PRM0004Code.CART.getType().equals(prd.getCpnKindCd()))
//                .distinct()
//                .collect(Collectors.toList());
//        return productCouponsVoList;
        return null;
    }

    private List<ProductCouponsVo> calculateDcAmt(List<ProductCouponsVo> productCouponsVoList, PromotionRequestVo reqVO){
        log.info("--------장바구니 쿠폰 할인 금액 계산");
//        for (ProductCouponsVo prd : productCouponsVoList) {
//
//            int includeGoods = reqVO.getProducts().stream()
//                    .filter(vo -> Arrays.stream(prd.getGoodsNo().split(",")).anyMatch(Predicate.isEqual(vo.getGoodsNo())))
//                    .mapToInt(ProductVo::getPrdDcPrice).sum(); // 쿠폰 해당 상품 금액 sum
//
//            int allGoods = reqVO.getProducts().stream().mapToInt(ProductVo::getPrdDcPrice).sum(); // 모든 상품 금액 sum
//
//            if (includeGoods >= prd.getMinPurAmt()) {
//                int dcPrice = PRM0003Code.PRICE.getType().equals(prd.getDcCcd()) ? prd.getDcVal() : (int) (includeGoods * (prd.getDcVal() * 0.01));
//                prd.setDcPrice(Math.min(dcPrice, prd.getMaxDcAmt()));
//                prd.setTotalPrice(allGoods - prd.getDcPrice());
//            }
//        }
        return productCouponsVoList;
    }

    private CartCouponResponseVo calculateMaxBenefit(List<ProductCouponsVo> productCouponsVoList, String mbrNo){
        log.info("-------최대 할인 적용 쿠폰 확인");
//        productCouponsVoList.stream()
//                .max(Comparator.comparing(ProductCouponsVo::getDcPrice))
//                .ifPresent(couponsVo -> couponsVo.setMaxBenefitYn("Y"));

        return CartCouponResponseVo.builder()
                .promotionProductList(productCouponsVoList)
                .memberNo(mbrNo)
                .build();
    }
}
