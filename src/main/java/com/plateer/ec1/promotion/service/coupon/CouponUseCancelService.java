package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.mapper.PromotionTrxMapper;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponUseCancelService {

    private final PromotionMapper promotionMapper;
    private final PromotionTrxMapper promotionTrxMapper;

    public Long useCoupon(PromotionRequestVo vo){
        return 1L;
    }

    public Long cancelCoupon(PromotionRequestVo vo){
        promotionMapper.selectAvailableRestoreCoupon(vo.getPrmNo());
        CcCpnIssueModel cpnIssueModel = CcCpnIssueModel
                .builder()
                .mbrNo(vo.getMbrNo())
                .prmNo(vo.getPrmNo())
                .orgCpnIssNo(vo.getCouponIssueNo())
                .build();
        promotionTrxMapper.insertDownloadCoupon(cpnIssueModel);
        return 1L;
    }

    public boolean verifyCoupon(PromotionRequestVo vo){
        return true;
    }
}
