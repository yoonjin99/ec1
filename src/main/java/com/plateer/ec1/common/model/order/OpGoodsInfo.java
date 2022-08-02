package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OpGoodsInfo {
    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private String goodsSellTpCd;
    private String goodsDlvTpCd;
    private String goodsNm;
    private String itemNm;
    private Long sellAmt;
    private Long sellDcAmt;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

    public static List<OpGoodsInfo> createGeneralData(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView){
        return orderProductView.stream()
                .map(viewVo -> OpGoodsInfo.builder()
                                            .ordNo(orderRequest.getOrdNo())
                                            .ordGoodsNo(viewVo.getPrGoodsBaseModel().getGoodsNo())
                                            .ordItemNo(viewVo.getPrItemInfoModel().getItemNo())
                                            .goodsSellTpCd(viewVo.getPrGoodsBaseModel().getGoodsTpCd())
                                            .goodsDlvTpCd(viewVo.getPrGoodsBaseModel().getPrgsStatCd())
                                            .sellAmt(viewVo.getPrGoodsBaseModel().getSalePrc())
                                            .sellDcAmt(viewVo.getPrGoodsBaseModel().getPrmPrc())
                                            .build())
                .collect(Collectors.toList());
    }

}
