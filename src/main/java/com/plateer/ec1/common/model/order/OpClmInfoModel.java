package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdGoodsInfoVo;
import com.plateer.ec1.order.vo.OrderProductViewVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class OpClmInfoModel {
    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordClmTpCd;
    private String ordPrgsScd;
    private String dvRvtCcd;
    private Long ordAmt;
    private Integer ordCnt;
    private Integer cnclCnt;
    private Integer rtgsCnt;
    private Integer dvGrpNo;
    private LocalDateTime ordClmReqDtime;
    private LocalDateTime ordClmAcptDtime;
    private LocalDateTime ordClmCmtDtime;
    private String clmRsnCd;
    private String clmDtlRsnTt;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String clmNo;
    private Integer orgProcSeq;

    // TODO: 주문순번 -> cnt++ / 주문수량을 넣어서 들고오기
    public static List<OpClmInfoModel> createGeneralData(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView){
        List<OpClmInfoModel> clmInfoModels = new ArrayList<>();
        for(OrderProductViewVo viewVo : orderProductView){
            for(OrdGoodsInfoVo goodsInfoVo : orderRequest.getOrdGoodsInfoVo()){
                if(goodsInfoVo.getOrdGoodsNo().equals(viewVo.getPrGoodsBaseModel().getGoodsNo()) && goodsInfoVo.getOrdItemNo().equals(viewVo.getPrItemInfoModel().getItemNo())){
                    OpClmInfoModel clmInfoModel = OpClmInfoModel.builder()
                            .ordNo(orderRequest.getOrdNo())
                            .ordGoodsNo(viewVo.getPrGoodsBaseModel().getGoodsNo())
                            .ordItemNo(viewVo.getPrItemInfoModel().getItemNo())
                            .procSeq(1)
                            .ordClmTpCd("O") // 주문클레임유형코드
                            .dvRvtCcd("10") //  배송회수구분코드
                            .ordCnt(goodsInfoVo.getOrdCnt()) // 주문수량
                            .ordAmt(viewVo.getPrGoodsBaseModel().getPrmPrc() > 0 ? viewVo.getPrGoodsBaseModel().getPrmPrc() : viewVo.getPrGoodsBaseModel().getSalePrc()) // 주문금액
                            .ordPrgsScd("10") //주문진행상태코드
                            .ordClmAcptDtime(LocalDateTime.now())
                            .ordClmAcptDtime(LocalDateTime.now())
                            .build();
                    clmInfoModels.add(clmInfoModel);
                }
            }
        }
        return clmInfoModels;
    }
}
