package com.plateer.ec1.common.model.order;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.OPT0005Type;
import com.plateer.ec1.order.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@ToString
public class OpOrdBnfRelInfoModel {
    private String ordNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordBnfNo;
    private String aplyCnclCcd;
    private Long aplyAmt;
    private String clmNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

    public List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo) {
        List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdBnfRelInfoModels())){
            for(OpOrdBnfRelInfoModel rel : vo.getOpOrdBnfRelInfoModels()){
                rel.setProcSeq(rel.getProcSeq() + 1);
                rel.setAplyCnclCcd(vo.getClaimType().equals(ClaimType.RW.getType()) ? OPT0005Type.APLY.getType() : OPT0005Type.CNCL.getType());
                rel.setClmNo(vo.getClmNo());
                opOrdBnfRelInfoModelList.add(rel);
            }
        }
        return opOrdBnfRelInfoModelList;
    }
}
