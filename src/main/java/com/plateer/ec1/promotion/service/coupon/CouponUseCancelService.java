package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.mapper.PromotionTrxMapper;
import com.plateer.ec1.promotion.vo.CouponRequestVo;
import com.plateer.ec1.promotion.vo.CouponVo;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponUseCancelService {

    private final PromotionMapper promotionMapper;
    private final PromotionTrxMapper promotionTrxMapper;

    @Transactional
    public void cancelCoupon(CouponRequestVo vo){
        try {
            CouponVo couponVo = promotionMapper.selectAvailableRestoreCoupon(vo);
            if(!Objects.isNull(couponVo)){
                vo.setUseStrtDtime(couponVo.getPrmStrtDt());
                vo.setUseEndDtime(couponVo.getPrmEndDt());
                promotionTrxMapper.insertDownloadCoupon(vo);
            }
        }catch (Exception e){
            log.info("CouponUseCancelService cancelCoupon error : {}" , e.getMessage());
        }
    }

}
