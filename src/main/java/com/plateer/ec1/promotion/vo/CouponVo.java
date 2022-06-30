package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CouponVo {
    private Long prmNo;
    private String prmNm;
    private int dwlPsbCnt;
    private int psnDwlPsbCnt;
    private int totCnt;
    private int mbrCnt;
    private LocalDateTime prmStrtDt;
    private LocalDateTime prmEndDt;
}
