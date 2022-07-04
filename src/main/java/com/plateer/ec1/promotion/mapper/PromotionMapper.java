package com.plateer.ec1.promotion.mapper;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import com.plateer.ec1.promotion.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PromotionMapper {
    CouponVo selectAvailableCoupon(CouponRequestVo couponRequestVo);
    List<PromotionVo> selectDownloadCouponList(String mbrNo);

    CouponVo selectAvailableRestoreCoupon(Long prmNo);

    List<PromotionVo> selectAvailablePromotionList(PromotionRequestVo reqVO);

    CcMbrPntModel selectPrePoint(String mbrNo);
}
