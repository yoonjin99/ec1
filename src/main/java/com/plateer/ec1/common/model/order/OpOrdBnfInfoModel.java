package com.plateer.ec1.common.model.order;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.order.vo.OrdBnfInfoVo;
import com.plateer.ec1.order.vo.OrdGoodsInfoVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
public class OpOrdBnfInfoModel {
    private String ordBnfNo;
    private Long ordBnfAmt;
    private Long prmNo;
    private Long cpnIssNo;
    private Long ordCnclBnfAmt;
    private int degrCcd;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String cpnKndCd;
    private String ordNo;

    public static OpOrdBnfInfoModel createModel(OrdBnfInfoVo bnfInfoVo, String ordNo){
        return OpOrdBnfInfoModel.builder()
                .prmNo(bnfInfoVo.getPrmNo())
                .ordNo(ordNo)
                .cpnIssNo(bnfInfoVo.getCpnIssNo())
                .cpnKndCd(bnfInfoVo.getCpnKndCd())
                .degrCcd(bnfInfoVo.getDegrCcd())
                .ordBnfAmt(bnfInfoVo.getDcPrice())
                .ordCnclBnfAmt(0L)
                .build();
    }

    public List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo) {
        List<OpOrdBnfInfoModel> opOrdBnfInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdBnfInfoModels())){
            for(OpOrdBnfInfoModel bnf : vo.getOpOrdBnfInfoModels()){
                bnf.setOrdCnclBnfAmt(vo.getClaimType().equals(ClaimType.RW.getType()) ? 0L : bnf.getOrdBnfAmt());
                opOrdBnfInfoModelList.add(bnf);
            }
        }
        return opOrdBnfInfoModelList;
    }
}
