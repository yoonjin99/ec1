package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.mapper.PromotionTrxMapper;
import com.plateer.ec1.promotion.vo.CouponRequestVo;
import com.plateer.ec1.promotion.vo.CouponVo;
import com.plateer.ec1.promotion.vo.PromotionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadCouponService {

    private final PromotionMapper promotionMapper;
    private final PromotionTrxMapper promotionTrxMapper;

    // 다운로드 가능 여부
    private CouponVo checkAvailableDownloadCoupon(CouponRequestVo couponRequestVo){
        log.info("다운로드 가능 수량 체크 -------------------");
        CouponVo couponVo = promotionMapper.selectAvailableCoupon(couponRequestVo);
        if(couponVo.getDwlPsdCnt() > couponVo.getTotCnt()){
            if(couponVo.getPsnDwlPsbCnt() > couponVo.getMbrCnt()){
                return couponVo;
            }
        }
        return null;
    }

    // 다운로드
    @Transactional
    public List<PromotionVo> downloadCoupon(CouponRequestVo couponRequestVo){
        CouponVo couponVo = checkAvailableDownloadCoupon(couponRequestVo);
        if(!Objects.isNull(couponVo)){
            log.info("다운로드 시작 ------------------------");
            couponRequestVo.setUseStrtDtime(couponVo.getPrmStrtDt());
            couponRequestVo.setUseEndDtime(couponVo.getPrmEndDt());
            promotionTrxMapper.insertDownloadCoupon(couponRequestVo);
        }
        return promotionMapper.selectDownloadCouponList(couponRequestVo.getMbrNo());
    }
}
