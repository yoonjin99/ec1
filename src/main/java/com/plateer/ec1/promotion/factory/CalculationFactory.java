package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.vo.BaseResponseVo;
import com.plateer.ec1.promotion.vo.PromotionRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CalculationFactory {

    private final Map<PromotionType, Calculation> map = new HashMap();

    public CalculationFactory(List<Calculation> calculations) {
        calculations.forEach(calculation -> map.put(calculation.getType(), calculation));
    }

    public BaseResponseVo getPromotionCalculationData(PromotionRequestVo vo, PromotionType type){
        log.info("-------CalculationFactory start-------");
        return map.get(type).getCalculationData(vo);
    }
}
