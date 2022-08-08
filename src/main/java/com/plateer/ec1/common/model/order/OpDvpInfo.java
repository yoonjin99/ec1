package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdDvpAreaInfoVo;
import com.plateer.ec1.order.vo.OrdDvpInfo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class OpDvpInfo {
    private Integer dvGrpNo;
    private String ordNo;
    private Integer dvpSeq;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String dvMthdCd;

    public static OpDvpInfo createModel(String ordNo, int dvpGrpNo, int dvpSeq){
        return OpDvpInfo.builder()
                .ordNo(ordNo)
                .dvGrpNo(dvpGrpNo)
                .dvpSeq(dvpSeq)
                .dvMthdCd("10")
                .build();
    }
}
