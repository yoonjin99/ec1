package com.plateer.ec1.promotion.mapper;

import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import com.plateer.ec1.promotion.vo.CouponRequestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionTrxMapper {
    int insertDownloadCoupon(CouponRequestVo couponRequestVo);

    Long useCancelPoint(CcMbrPntModel ccMbrPntModel);
}
