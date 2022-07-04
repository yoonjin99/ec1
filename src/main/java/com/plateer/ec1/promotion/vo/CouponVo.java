package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Setter
@Getter
@Slf4j
public class CouponVo {
    private Long prmNo;
    private String prmNm;
    private int dwlPsbCnt;
    private int psnDwlPsbCnt;
    private int totCnt;
    private int mbrCnt;
    private LocalDateTime prmStrtDt;
    private LocalDateTime prmEndDt;

    public CouponVo downloadValidCheck(CouponVo couponVo) {
        if(couponVo.getDwlPsbCnt() > couponVo.getTotCnt()){
            if(couponVo.getPsnDwlPsbCnt() > couponVo.getMbrCnt()){
                return couponVo;
            }
        }
        return null;
    }
}
