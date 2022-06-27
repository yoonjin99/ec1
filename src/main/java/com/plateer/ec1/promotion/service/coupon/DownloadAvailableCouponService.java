package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.vo.PromotionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadAvailableCouponService {

    private final PromotionMapper promotionMapper;

    public List<PromotionVo> getDownloadAvailableCouponList(String memberNo){
        return promotionMapper.selectAvailableCouponList(memberNo);
    }
}
