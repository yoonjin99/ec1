package com.plateer.ec1.order.mapper.data;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.vo.OrderBenefitRelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDataTrxMapper {
    void insertOrderBase(OpOrdBaseModel OpOrdBaseModel);
    void insertOrderGoods(List<OpGoodsInfo> opGoodsInfoList);
    void insertOrderClaim(List<OpClmInfoModel> opClmInfoModelList);
    void insertOrderAreaInfo(List<OpDvpAreaInfo> opDvpAreaInfo);
    void insertOrderDvpInfo(List<OpDvpInfo> opDvpInfoList);
    String insertOrderBnf(OpOrdBnfInfoModel opOrdBnfInfoModelList);
    void insertOrderBnfRel(OrderBenefitRelVo opOrdBnfRelInfoModelList);
    void insertOrderCost(List<OpOrdCostInfoModel> opOrdCostInfoModelList);
}
