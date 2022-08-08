package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdDvpAreaInfoVo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class OpDvpAreaInfo {
    private String ordNo;
    private Integer dvpSeq;
    private String rmtiNm;
    private String rmtiHpNo;
    private String rmtiAddr;
    private String rmtiAddrDtl;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

    public static OpDvpAreaInfo createModel(String ordNo, OrdDvpAreaInfoVo info){
        return OpDvpAreaInfo.builder()
                .ordNo(ordNo)
                .dvpSeq(info.getDvpSeq())
                .rmtiHpNo(info.getRmtiHpNo())
                .rmtiNm(info.getRmtiNm())
                .rmtiAddrDtl(info.getRmtiAddrDtl())
                .rmtiAddr(info.getRmtiAddr())
                .build();
    }
}
