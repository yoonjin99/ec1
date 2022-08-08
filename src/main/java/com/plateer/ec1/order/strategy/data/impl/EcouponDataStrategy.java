package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.enums.DataType;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.plateer.ec1.order.service.OrderModelCreators.*;

@Slf4j
@Component
public class EcouponDataStrategy implements DataStrategy {
    @Override
    public OrderVo create(OrderRequestVo orderRequest, List<OrderProductViewVo> orderProductView) {
        log.info("-----------------ecoupon data create start-----------------");
        Pair<List<OpClmInfoModel>, List<OrderBenefitRelVo>> clmBnfRel = createClmBnfRel(orderRequest);

        return OrderVo.builder()
                .opOrdBaseModel(OpOrdBaseModel.createGeneralData(orderRequest))
                .opGoodsInfoList(commonOpGoodsInfo(orderRequest,orderProductView))
                .opClmInfoModelList(clmBnfRel.getFirst())
                .opDvpAreaInfo(commonOpDvpAreaInfo(orderRequest))
                .opDvpInfoList(commonOpDvpInfo(orderRequest))
                .opOrdCostInfoModelList(commonOpOrdCostInfoModel(orderRequest))
                .opOrdBnfRelInfoModelList(clmBnfRel.getSecond())
                .opOrdBnfInfoModelList(commonOpOrdBnfInfoModel(orderRequest))
                .build();
    }

    @Override
    public DataType getType() {
        return DataType.ECOUPON;
    }

    private Pair<List<OpClmInfoModel>,List<OrderBenefitRelVo>> createClmBnfRel(OrderRequestVo orderRequest){
        List<OpClmInfoModel> clmInfoModels = new ArrayList<>();
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();

        int cnt = 1;
        for(OrdGoodsInfoVo goodsInfoVo : orderRequest.getOrdGoodsInfoVo()){
            for(int i=0; i < goodsInfoVo.getOrdCnt(); i++){
                int dvGrpNo = getDvpGrp(orderRequest).get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo() + i);
                clmInfoModels.add(OpClmInfoModel.createModel(orderRequest.getOrdNo(), dvGrpNo, cnt, goodsInfoVo, 1));
                opOrdBnfRelInfoModelList.addAll(OrderBenefitRelVo.createGeneralData(goodsInfoVo, orderRequest, cnt));
                cnt++;
            }
        }

        return Pair.of(clmInfoModels, opOrdBnfRelInfoModelList);
    }

    private Map<String, Integer> getDvpGrp(OrderRequestVo orderRequestVo){
        Map<String, Integer> map = new HashMap<String, Integer>();
        int i = 0;
        for(OrdDvpAreaInfoVo vo : orderRequestVo.getOrdDvpAreaInfoVo()){
            for(OrdDvpInfo dvp : vo.getOrdDvpInfo()){
                for(GoodsInfoVo goods : dvp.getGoodsInfo()){
                    map.put(goods.getOrdGoodsNo() + goods.getOrdItemNo() + i, dvp.getDvGrpNo());
                }
            }
            i++;
        }
        return map;
    }
}
