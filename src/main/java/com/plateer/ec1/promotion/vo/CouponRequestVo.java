package com.plateer.ec1.promotion.vo;

import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouponRequestVo {
    private String mbrNo;
    private Long prmNo;
    private Long orgCpnIssNo;
    private Long cpnIssNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime useStrtDtime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime useEndDtime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysRegDtime;
    private String sysRegrId = "admin";
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime sysModDtime;
    private String sysModrId = "admin";

    public CouponRequestVo createRequest(OpOrdBnfInfoModel bnf){
        CouponRequestVo couponRequestVo = new CouponRequestVo();
        couponRequestVo.setCpnIssNo(bnf.getCpnIssNo());
        couponRequestVo.setPrmNo(bnf.getPrmNo());
        couponRequestVo.setMbrNo("test01");

        return couponRequestVo;
    }
}
