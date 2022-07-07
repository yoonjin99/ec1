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
        CartCouponResponseVo cartCouponResponseVo = calculateMaxBenefit(calculateDcAmtList, vo.getMbrNo());
        return cartCouponResponseVo;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.cartCoupon;
    }

    private List<CouponProductsVo> getAvailablePromotionData(PromotionRequestVo reqVO){
        log.info("-------적용 가능한 장바구니 쿠폰 목록 가져옴");
        List<PromotionVo> promotionList = promotionMapper.selectAvailableCartPromotionList(reqVO);
        promotionList =  promotionList.stream()
                .filter(prd -> PRM0004Code.CART.getType().equals(prd.getCpnKindCd()))
                .collect(Collectors.toList());
        
        List<CouponProductsVo> couponProductsVo = new ArrayList<>();
        for(PromotionVo prm : promotionList){
            CouponProductsVo couponsVo = new CouponProductsVo();
            List<ProductVo> productVoList = new ArrayList<>();

            String[] goodsNo = prm.getGoodsNo().split(",");
            for(String no : goodsNo){
                for(ProductVo prd : reqVO.getProducts()){
                    if(no.equals(prd.getGoodsNo())){
                        productVoList.add(prd);
                    }
                }
            }

            couponsVo.setPromotion(prm);
            couponsVo.setProductList(productVoList);
            couponProductsVo.add(couponsVo);
        }
        
        return couponProductsVo;
    }

    private List<CouponProductsVo> calculateDcAmt(List<CouponProductsVo> productCouponsVoList, PromotionRequestVo reqVO){
        log.info("--------장바구니 쿠폰 할인 금액 계산");
        for (Iterator<CouponProductsVo> iterator = productCouponsVoList.listIterator(); iterator.hasNext();) {
            CouponProductsVo prd = iterator.next();
            // 할인금액 계산하기
            // 1. 프로모션 상품 sum
            int sum = 0;
            for(ProductVo vo : prd.getProductList()){
                sum += vo.getPrdPrice() * vo.getOrderCnt();
            }
            // 2. 정률, 정액 할인에 따라 할인 금액 return
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
