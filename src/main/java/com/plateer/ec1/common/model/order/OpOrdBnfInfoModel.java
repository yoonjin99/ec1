package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdBnfInfoVo;
import com.plateer.ec1.order.vo.OrdGoodsInfoVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
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

    public static List<OpOrdBnfInfoModel> createGeneralData(OrderRequestVo orderRequest){
        List<OpOrdBnfInfoModel> ordBnfInfoModels
                = orderRequest.getOrdGoodsInfoVo().stream()
                .flatMap(goods -> goods.getOrdBnfInfoVo().stream())
                .map(bnf -> createModel(bnf, orderRequest.getOrdNo()))
                .collect(Collectors.toList());

        orderRequest.getOrdBnfInfoVo().stream()
                .map(bnf -> createModel(bnf, orderRequest.getOrdNo()))
                .forEach(ordBnfInfoModels::add);
        return ordBnfInfoModels;
    }

    private static OpOrdBnfInfoModel createModel(OrdBnfInfoVo bnfInfoVo, String ordNo){
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
}
