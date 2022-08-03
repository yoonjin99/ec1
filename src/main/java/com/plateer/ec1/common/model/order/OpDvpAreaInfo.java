package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdDvpAreaInfoVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
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

    public static List<OpDvpAreaInfo> createGeneralData(OrderRequestVo orderRequest){
        return orderRequest.getOrdDvpAreaInfoVo().stream()
                .map(infoVo -> OpDvpAreaInfo.builder()
                                            .ordNo(orderRequest.getOrdNo())
                                            .dvpSeq(infoVo.getDvpSeq())
                                            .rmtiHpNo(infoVo.getRmtiHpNo())
                                            .rmtiNm(infoVo.getRmtiNm())
                                            .rmtiAddrDtl(infoVo.getRmtiAddrDtl())
                                            .rmtiAddr(infoVo.getRmtiAddr())
                                            .build())
                .collect(Collectors.toList());
    }
}
