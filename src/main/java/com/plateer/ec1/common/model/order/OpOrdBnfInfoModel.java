package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.OrdBnfInfoVo;
import com.plateer.ec1.order.vo.OrdGoodsInfoVo;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class OpOrdBnfInfoModel {
    private String ordBnfNo;
    private Integer ordBnfAmt;
    private Long prmNo;
    private Long cpnIssNo;
    private Integer ordCnclBnfAmt;
    private Integer degrCcd;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String cpnKndCd;

    public static List<OpOrdBnfInfoModel> createGeneralData(OrderRequestVo orderRequest){
        List<OpOrdBnfInfoModel> ordBnfInfoModels = new ArrayList<>();
        for(OrdGoodsInfoVo goods : orderRequest.getOrdGoodsInfoVo()){
            for(OrdBnfInfoVo bnf : goods.getOrdBnfInfoVo()){
                OpOrdBnfInfoModel model = OpOrdBnfInfoModel.builder().build();
                ordBnfInfoModels.add(model);
            }
        }

        for(OrdBnfInfoVo bnfInfoVo : orderRequest.getOrdBnfInfoVo()){
            OpOrdBnfInfoModel model = OpOrdBnfInfoModel.builder().build();
            ordBnfInfoModels.add(model);
        }

        return ordBnfInfoModels;
    }

}
