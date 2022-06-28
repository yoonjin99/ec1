package com.plateer.ec1.promotion.mapper;

import com.plateer.ec1.promotion.vo.PromotionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PromotionTrxMapper {
    int insertDownloadCoupon(Map<String, Object> map);
}
