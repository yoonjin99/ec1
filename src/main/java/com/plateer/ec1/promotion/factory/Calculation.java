package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.vo.BaseResponseVo;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;

public interface Calculation {
    public BaseResponseVo getCalculationData(PromotionRequestVo vo);
    PromotionType getType();
}
