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
                .map(viewVo -> createModel(orderRequest.getOrdNo(), viewVo))
                .collect(Collectors.toList());
    }

    private static OpGoodsInfo createModel(String ordNo, OrderProductViewVo viewVo){
        return OpGoodsInfo.builder()
                .ordNo(ordNo)
                .ordGoodsNo(viewVo.getGoodsNo())
                .ordItemNo(viewVo.getItemNo())
                .goodsNm(viewVo.getGoodsNm())
                .itemNm(viewVo.getItemNm())
                .goodsSellTpCd(viewVo.getGoodsTpCd())
                .goodsDlvTpCd(viewVo.getPrgsStatCd())
                .sellAmt(viewVo.getSalePrc())
                .sellDcAmt(viewVo.getPrmPrc())
                .build();
    }

}
