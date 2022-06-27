package com.plateer.ec1.promotion.service.coupon;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.mapper.PromotionMapper;
import com.plateer.ec1.promotion.vo.PromotionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadCouponService {

    private final PromotionMapper promotionMapper;

    // 다운로드 가능 여부
    private boolean checkAvailableDownloadCoupon(String memberNo, PromotionVo promotion){
        log.info("다운로드 가능 수량 체크 -------------------");
        // 수량체크만 해주면 됨
        Map<String, Object> map = new HashMap<String,Object>(){{
            put("mbrNo", memberNo);
            put("prmNo", promotion.getPrmNo());
        }};
        //predicate 로 할까
        return promotionMapper.selectDownloadAvailableCoupon(map) != null ? true : false;
    }

    // 다운로드
    @Transactional
    public List<PromotionVo> downloadCoupon(String memberNo, PromotionVo promotion){
        boolean validateCheck = checkAvailableDownloadCoupon(memberNo, promotion);
        if(validateCheck){
            log.info("다운로드 시작 ------------------------");
            Map<String, Object> map = new HashMap<String,Object>(){{
                put("mbrNo", memberNo);
                put("prmNo", promotion.getPrmNo());
                put("sysRegrId", "admin");
                put("sysModrId", "admin");
            }};
            promotionMapper.insertDownloadCoupon(map);
        }
        return promotionMapper.selectDownloadCouponList(memberNo);
    }
}
