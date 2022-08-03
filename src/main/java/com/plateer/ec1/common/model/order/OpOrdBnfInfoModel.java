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
        List<OpOrdBnfInfoModel> ordBnfInfoModels = new ArrayList<>();
        for(OrdGoodsInfoVo goods : orderRequest.getOrdGoodsInfoVo()){
            for(OrdBnfInfoVo bnf : goods.getOrdBnfInfoVo()){
                OpOrdBnfInfoModel model = OpOrdBnfInfoModel.builder()
                        .ordNo(orderRequest.getOrdNo())
                        .prmNo(bnf.getPrmNo())
                        .cpnKndCd(bnf.getCpnKndCd())
                        .degrCcd(bnf.getDegrCcd())
                        .ordBnfAmt(bnf.getDcPrice())
                        .ordCnclBnfAmt(0L)
                        .build();
                ordBnfInfoModels.add(model);
            }
        }

        for(OrdBnfInfoVo bnfInfoVo : orderRequest.getOrdBnfInfoVo()){
            OpOrdBnfInfoModel model = OpOrdBnfInfoModel.builder()
                    .prmNo(bnfInfoVo.getPrmNo())
                    .ordNo(orderRequest.getOrdNo())
                    .cpnKndCd(bnfInfoVo.getCpnKndCd())
                    .degrCcd(bnfInfoVo.getDegrCcd())
                    .ordBnfAmt(bnfInfoVo.getDcPrice())
                    .ordCnclBnfAmt(0L)
                    .build();
            ordBnfInfoModels.add(model);
        }

        return ordBnfInfoModels;
    }

}
