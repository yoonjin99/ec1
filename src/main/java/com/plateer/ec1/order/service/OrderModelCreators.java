package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.mapper.validator.OrderValidatiorMapper;
import com.plateer.ec1.order.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.compiler.nodes.calc.IntegerTestNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderModelCreators {

    public static List<OpGoodsInfo> commonOpGoodsInfo(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView){
        return orderProductView.stream()
                .map(viewVo -> OpGoodsInfo.createModel(orderRequest.getOrdNo(), viewVo))
                .collect(Collectors.toList());
    }

    public static List<OpDvpAreaInfo> commonOpDvpAreaInfo(OrderRequestVo orderRequest){
         return orderRequest.getOrdDvpAreaInfoVo().stream()
                .map(infoVo -> OpDvpAreaInfo.createModel(orderRequest.getOrdNo(), infoVo))
                .collect(Collectors.toList());
    }

    public static List<OpDvpInfo> commonOpDvpInfo(OrderRequestVo orderRequest){
        List<OpDvpInfo> opDvpInfos = new ArrayList<>();
        for(OrdDvpAreaInfoVo ordDvpAreaInfoVo : orderRequest.getOrdDvpAreaInfoVo()){
            for(OrdDvpInfo ordDvpInfo : ordDvpAreaInfoVo.getOrdDvpInfo()){
                opDvpInfos.add(OpDvpInfo.createModel(orderRequest.getOrdNo(), ordDvpInfo.getDvGrpNo(), ordDvpAreaInfoVo.getDvpSeq()));
            }
        }
        return opDvpInfos;
    }

    public static List<OpOrdCostInfoModel> commonOpOrdCostInfoModel(OrderRequestVo orderRequest){
        return orderRequest.getOrdDvpAreaInfoVo().stream()
                .flatMap(ordDvpAreaInfoVo -> ordDvpAreaInfoVo.getOrdDvpInfo().stream())
                .map(ordDvpInfo -> OpOrdCostInfoModel.createModel(orderRequest.getOrdNo(), ordDvpInfo.getDvGrpNo()))
                .collect(Collectors.toList());
    }

    public static List<OpOrdBnfInfoModel> commonOpOrdBnfInfoModel(OrderRequestVo orderRequest){
        List<OpOrdBnfInfoModel> ordBnfInfoModels
                = new ArrayList<>();
        for (OrdGoodsInfoVo goods : orderRequest.getOrdGoodsInfoVo()) {
            if(!Objects.isNull(goods.getOrdBnfInfoVo())){
                for (OrdBnfInfoVo bnfInfoVo : goods.getOrdBnfInfoVo()) {
                    OpOrdBnfInfoModel model = OpOrdBnfInfoModel.createModel(bnfInfoVo, orderRequest.getOrdNo());
                    ordBnfInfoModels.add(model);
                }
            }
        }

        if(!Objects.isNull(orderRequest.getOrdBnfInfoVo())){
            for (OrdBnfInfoVo bnf : orderRequest.getOrdBnfInfoVo()) {
                OpOrdBnfInfoModel model = OpOrdBnfInfoModel.createModel(bnf, orderRequest.getOrdNo());
                ordBnfInfoModels.add(model);
            }
        }

        return ordBnfInfoModels;
    }

}
