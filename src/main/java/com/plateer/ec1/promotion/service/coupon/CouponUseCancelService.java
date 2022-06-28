package com.plateer.ec1.promotion.service.coupon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponUseCancelService {
    private Long couponIssueNo;
    private String memberNo;

    public Long useCoupon(){
        return 1L;
    }

    public Long cancelCoupon(){
        return 1L;
    }

    public boolean verifyCoupon(){
        return true;
    }
}
