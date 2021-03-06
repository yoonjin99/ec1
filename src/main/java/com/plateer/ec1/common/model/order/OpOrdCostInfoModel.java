package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdDvpAreaInfoVo;
import com.plateer.ec1.order.vo.OrdDvpInfo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class OpOrdCostInfoModel {
    private String ordCstNo;
    private Integer dvGrpNo;
    private String aplyCcd;
    private String orgOrdCstNo;
    private String clmNo;
    private String ordNo;
    private String dvAmtTpCd;
    private Long orgDvAmt;
    private Long dvBnfAmt;
    private Long aplyDvAmt;
    private String imtnRsnCcd;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String dvPlcTpCd;
    private Long cnclDvAmt;

    public static List<OpOrdCostInfoModel> createGeneralData(OrderRequestVo orderRequest){
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        for(OrdDvpAreaInfoVo ordDvpAreaInfoVo : orderRequest.getOrdDvpAreaInfoVo()){
            for(OrdDvpInfo ordDvpInfo : ordDvpAreaInfoVo.getOrdDvpInfo()){
                OpOrdCostInfoModel opOrdCostInfoModel = OpOrdCostInfoModel.builder()
                        .ordNo(orderRequest.getOrdNo())
                        .aplyCcd("10")
                        .dvGrpNo(ordDvpInfo.getDvGrpNo())
                        .dvAmtTpCd("10")
                        .dvPlcTpCd("10")
                        .dvAmtTpCd("10")
                        .orgDvAmt(0L)
                        .dvBnfAmt(0L)
                        .aplyDvAmt(0L)
                        .build();
                opOrdCostInfoModelList.add(opOrdCostInfoModel);
            }
        }
//    TODO : dvamttcCd - 배송비구분코드, dvPlcTpCd  -배송비정책구분코드 db에 추가 및 enum 생성

        return opOrdCostInfoModelList;
    }

}
