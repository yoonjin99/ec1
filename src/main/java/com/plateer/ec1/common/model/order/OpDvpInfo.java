package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdDvpAreaInfoVo;
import com.plateer.ec1.order.vo.OrdDvpInfo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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

    public static List<OpDvpInfo> createGeneralData(OrderRequestVo orderRequest){
        List<OpDvpInfo> opDvpInfos = new ArrayList<>();
        for(OrdDvpAreaInfoVo ordDvpAreaInfoVo : orderRequest.getOrdDvpAreaInfoVo()){
            for(OrdDvpInfo ordDvpInfo : ordDvpAreaInfoVo.getOrdDvpInfo()){
                OpDvpInfo opDvpInfo = OpDvpInfo.builder()
                        .ordNo(orderRequest.getOrdNo())
                        .dvMthdCd("10")
                        .build();
                opDvpInfos.add(opDvpInfo);
            }
        }
        return opDvpInfos;
    }
}
