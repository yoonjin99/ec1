package com.plateer.ec1.common.model.order;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.OPT0003Type;
import com.plateer.ec1.common.code.order.OPT0004Type;
import com.plateer.ec1.order.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Builder(toBuilder = true)
@ToString
@Getter
@Setter
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

    public static OpClmInfoModel createModel(String ordNo, int dvGrpNo, int cnt, OrdGoodsInfoVo viewVo, int ordCnt){
        return OpClmInfoModel.builder()
                .ordNo(ordNo)
                .ordGoodsNo(viewVo.getOrdGoodsNo())
                .ordItemNo(viewVo.getOrdItemNo())
                .ordSeq(cnt)
                .cnclCnt(0)
                .rtgsCnt(0)
                .procSeq(1)
                .dvGrpNo(dvGrpNo)
                .ordClmTpCd("O") // 주문클레임유형코드
                .dvRvtCcd("10") //  배송회수구분코드
                .ordCnt(ordCnt) // 주문수량
                .ordAmt(viewVo.getPrmPrc() > 0 ? viewVo.getPrmPrc() : viewVo.getSalePrc()) // 주문금액 -> 수정하기
                .ordPrgsScd("10") //주문진행상태코드
                .ordClmReqDtime(LocalDateTime.now())
                .ordClmAcptDtime(LocalDateTime.now())
                .build();
    }

    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo){
        List<OpClmInfoModel> opClmInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpClmInfoModels())){
            for(OpClmInfoModel clm : vo.getOpClmInfoModels()){
                clm.setOrgProcSeq(clm.getProcSeq());
                clm.setProcSeq(clm.getProcSeq() +  1);
                clm.setClmNo(vo.getClmNo());
                clm.setOrdClmReqDtime(LocalDateTime.now());
                clm.setOrdClmAcptDtime(LocalDateTime.now());
                opClmInfoModelList.add(clm);
            }
        }
        return opClmInfoModelList;
    }
}
