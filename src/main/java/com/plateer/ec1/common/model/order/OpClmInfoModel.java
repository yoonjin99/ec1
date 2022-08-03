package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@ToString
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

    public static List<OpClmInfoModel> createGeneralData(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView){

        Map<String, Integer> map = new HashMap<String, Integer>();
        for(OrdDvpAreaInfoVo vo : orderRequest.getOrdDvpAreaInfoVo()){
            for(OrdDvpInfo dvp : vo.getOrdDvpInfo()){
                for(GoodsInfoVo goods : dvp.getGoodsInfo()){
                    map.put(goods.getOrdGoodsNo() + goods.getOrdItemNo(), dvp.getDvGrpNo());
                }
            }
        }

        List<OpClmInfoModel> clmInfoModels = new ArrayList<>();
        for(OrderProductViewVo viewVo : orderProductView){
            int cnt = 1;
            for(OrdGoodsInfoVo goodsInfoVo : orderRequest.getOrdGoodsInfoVo()){
                if(goodsInfoVo.getOrdGoodsNo().equals(viewVo.getGoodsNo()) && goodsInfoVo.getOrdItemNo().equals(viewVo.getItemNo())){
                    int dvGrpNo = map.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo());
                    OpClmInfoModel clmInfoModel = OpClmInfoModel.builder()
                            .ordNo(orderRequest.getOrdNo())
                            .ordGoodsNo(viewVo.getGoodsNo())
                            .ordItemNo(viewVo.getItemNo())
                            .ordSeq(cnt)
                            .cnclCnt(0)
                            .rtgsCnt(0)
                            .procSeq(1)
                            .dvGrpNo(dvGrpNo)
                            .ordClmTpCd("O") // 주문클레임유형코드
                            .dvRvtCcd("10") //  배송회수구분코드
                            .ordCnt(goodsInfoVo.getOrdCnt()) // 주문수량
                            .ordAmt(viewVo.getPrmPrc() > 0 ? viewVo.getPrmPrc() : viewVo.getSalePrc()) // 주문금액
                            .ordPrgsScd("10") //주문진행상태코드
                            .ordClmReqDtime(LocalDateTime.now())
                            .ordClmAcptDtime(LocalDateTime.now())
                            .build();
                    clmInfoModels.add(clmInfoModel);
                }
                cnt++;
            }
        }
        return clmInfoModels;
    }
}
