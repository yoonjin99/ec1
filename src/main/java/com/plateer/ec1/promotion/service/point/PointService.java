package com.plateer.ec1.promotion.service.point;

import com.plateer.ec1.common.code.promotion.PRM0011Code;
import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.mapper.PromotionTrxMapper;
import com.plateer.ec1.promotion.vo.PointRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PromotionMapper promotionMapper;
    private final PromotionTrxMapper promotionTrxMapper;

    @Transactional
    public void usePoint(PointRequestVo vo){
        // TODO : 포인트를 사용할게 없다면 ?? -라면 ??
        log.info("포인트 사용");
        try {
            CcMbrPntModel insertCcMbrPntModel = CcMbrPntModel.builder()
                    .mbrNo(vo.getMemberNo())
                    .pntBlc(balPoint(vo))
                    .svUseCcd(PRM0011Code.USE.getType())
                    .svUseAmt(vo.getPointAmt())
                    .build();
            promotionTrxMapper.useCancelPoint(insertCcMbrPntModel);
        }catch (Exception e){
            log.info("PointService usePoint error" + e.getMessage());
        }
    }

    @Transactional
    public void cancelPoint(PointRequestVo vo){
        log.info("포인트 사용 취소");
        try {
            CcMbrPntModel insertCcMbrPntModel = CcMbrPntModel.builder()
                    .mbrNo(vo.getMemberNo())
                    .pntBlc(balPoint(vo))
                    .svUseCcd(PRM0011Code.ADD.getType())
                    .svUseAmt(vo.getPointAmt())
                    .build();
            promotionTrxMapper.useCancelPoint(insertCcMbrPntModel);
        }catch (Exception e){
            log.info("PointService usePoint error" + e.getMessage());
        }
    }

    private Long balPoint(PointRequestVo vo){
        CcMbrPntModel ccMbrPntModel = promotionMapper.selectPrePoint(vo.getMemberNo());
        if(vo.getSaveUseCcd().equals(PRM0011Code.USE.getType())) return ccMbrPntModel.getPntBlc() - vo.getPointAmt();
        else return ccMbrPntModel.getPntBlc() + vo.getPointAmt();
    }
}
