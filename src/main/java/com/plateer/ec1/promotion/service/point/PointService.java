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
    public Long usePoint(PointRequestVo vo){
        log.info("포인트 사용");
        try {
            CcMbrPntModel insertCcMbrPntModel = CcMbrPntModel.builder()
                    .mbrNo(vo.getMemberNo())
                    .pntBlc(balPoint(vo))
                    .svUseCcd(PRM0011Code.USE.getType())
                    .svUseAmt(vo.getPointAmt())
                    .build();
            promotionTrxMapper.useCancelPoint(insertCcMbrPntModel);
            return insertCcMbrPntModel.getPntHstSeq();
        }catch (Exception e){
            log.info("PointService usePoint error" + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Long cancelPoint(PointRequestVo vo){
        log.info("포인트 사용 취소");
        try {
            CcMbrPntModel insertCcMbrPntModel = CcMbrPntModel.builder()
                    .mbrNo(vo.getMemberNo())
                    .pntBlc(balPoint(vo))
                    .svUseCcd(PRM0011Code.ADD.getType())
                    .svUseAmt(vo.getPointAmt())
                    .build();
            promotionTrxMapper.useCancelPoint(insertCcMbrPntModel);
            return insertCcMbrPntModel.getPntHstSeq();
        }catch (Exception e){
            log.info("PointService usePoint error" + e.getMessage());
            throw e;
        }
    }

    private Long balPoint(PointRequestVo vo){
        CcMbrPntModel ccMbrPntModel = promotionMapper.selectPrePoint(vo.getMemberNo());
        if(vo.getSaveUseCcd().equals(PRM0011Code.USE.getType())) {
            if(ccMbrPntModel.getPntBlc() < vo.getPointAmt()){
                throw new IllegalStateException("총 포인트보다 결제 금액이 더 크기 때문에 포인트 결제가 불가능합니다.");
            }
            return ccMbrPntModel.getPntBlc() - vo.getPointAmt();
        } else {
            return ccMbrPntModel.getPntBlc() + vo.getPointAmt();
        }
    }
}
