package com.plateer.ec1.promotion.mapper;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.vo.PromotionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PromotionMapper {
    Long selectAvailableCoupon(Map<String, Object> param);
    List<PromotionVo> selectDownloadCouponList(String mbrNo);

    Long selectAvailableRestoreCoupon(Long prmNo);
}
