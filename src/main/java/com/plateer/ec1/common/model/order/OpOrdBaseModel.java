package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OpOrdBaseModel {
    private String ordNo;
    private String mbrNo;
    private String ordTpCd;
    private String ordSysCcd;
    private LocalDateTime ordReqDtime;
    private LocalDateTime ordCmtDtime;
    private String ordNm;
    private String ordSellNo;
    private String ordAddr;
    private String ordAddrDtl;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModrId;
    private String sysModDtim;

    public static OpOrdBaseModel createGeneralData(OrderRequestVo orderRequest){
        return OpOrdBaseModel.builder()
                .ordTpCd(orderRequest.getOrdBaseVo().getOrdTpCd())
                .ordSysCcd(orderRequest.getOrdBaseVo().getOrdSysCcd())
                .ordReqDtime(LocalDateTime.now())
                .rfndBnkCk(orderRequest.getOrdBaseVo().getRfndBnkCk())
                .rfndAcctNo(orderRequest.getOrdBaseVo().getRfndAcctNo())
                .rfndAcctOwnNm(orderRequest.getOrdBaseVo().getRfndAcctOwnNm())
                .build();
    }
}
