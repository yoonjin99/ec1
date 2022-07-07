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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartCouponCalculationImpl implements Calculation {

    private final PromotionMapper promotionMapper;

    @Override
    public BaseResponseVo getCalculationData(PromotionRequestVo vo) {
        log.info("-------CartCouponCalculation getCalculationData start");
        List<CouponProductsVo> getAvailablePromotionDataList = getAvailablePromotionData(vo);
        List<CouponProductsVo> calculateDcAmtList =  calculateDcAmt(getAvailablePromotionDataList,vo);
        return calculateMaxBenefit(calculateDcAmtList, vo.getMbrNo());
    }

    @Override
    public PromotionType getType() {
        return PromotionType.cartCoupon;
    }

    private List<CouponProductsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------적용 가능한 장바구니 쿠폰 목록 가져옴" + reqVO.toString());
        List<CouponProductsVo> couponProductsVoList = promotionMapper.selectAvailableCartPromotionList(reqVO);


        couponProductsVoList.forEach(cpn -> {
            for (ProductVo vo : reqVO.getProducts()) {
                for (ProductVo prd : cpn.getProductList()) {
                    if (!vo.getGoodsNo().equals(prd.getGoodsNo()) && !vo.getItemNo().equals(prd.getItemNo())) {
                        cpn.getProductList().remove(prd);
                    }
                    break;
                }
            }
        });

        for (CouponProductsVo cpn : couponProductsVoList) {
            reqVO.getProducts()
                    .forEach(vo -> cpn.getProductList().stream().filter(prd -> vo.getGoodsNo().equals(prd.getGoodsNo())
                            && vo.getItemNo().equals(prd.getItemNo())).forEach(prd -> prd.setOrderCnt(vo.getOrderCnt())));
        }

        return couponProductsVoList;
    }

    private List<CouponProductsVo> calculateDcAmt(List<CouponProductsVo> productCouponsVoList, PromotionRequestVo reqVO){
        log.info("--------장바구니 쿠폰 할인 금액 계산");
        for (Iterator<CouponProductsVo> iterator = productCouponsVoList.listIterator(); iterator.hasNext();) {
            CouponProductsVo prd = iterator.next();
            int sum = prd.getProductList().stream().mapToInt(vo -> vo.getPrdPrice() * vo.getOrderCnt()).sum();
            if (sum >= prd.getPromotion().getMinPurAmt()) {
                int dcPrice = PRM0003Code.PRICE.getType().equals(prd.getPromotion().getDcCcd()) ? prd.getPromotion().getDcVal() : (int) (sum * (prd.getPromotion().getDcVal() * 0.01));
                prd.getPromotion().setDcPrice(Math.min(dcPrice, prd.getPromotion().getMaxDcAmt()));
            }else{
                iterator.remove();
            }
        }
        return productCouponsVoList;
    }

    private CartCouponResponseVo calculateMaxBenefit(List<CouponProductsVo> productCouponsVoList, String mbrNo){
        log.info("-------최대 할인 적용 쿠폰 확인");
        productCouponsVoList.stream()
                .max(Comparator.comparing(couponProductsVo -> couponProductsVo.getPromotion().getDcPrice()))
                .ifPresent(couponProductsVo -> couponProductsVo.getPromotion().setMaxBenefitYn("Y"));

        return CartCouponResponseVo.builder()
                .promotionProductList(productCouponsVoList)
                .memberNo(mbrNo)
                .build();
    }
}
