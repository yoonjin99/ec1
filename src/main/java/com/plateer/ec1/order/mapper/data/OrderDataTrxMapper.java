package com.plateer.ec1.order.mapper.data;

import com.plateer.ec1.common.model.order.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDataTrxMapper {
    void insertOrderBase(OpOrdBaseModel OpOrdBaseModel);
    void insertOrderGoods(List<OpGoodsInfo> opGoodsInfoList);
    void insertOrderClaim(List<OpClmInfoModel> opClmInfoModelList);
    void insertOrderAreaInfo(List<OpDvpAreaInfo> opDvpAreaInfo);
    void insertOrderDvpInfo(List<OpDvpInfo> opDvpInfoList);
    void insertOrderBnf(List<OpOrdBnfInfoModel> opOrdBnfInfoModelList);
    void insertOrderBnfRel(List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModelList);
    void insertOrderCost(List<OpOrdCostInfoModel> opOrdCostInfoModelList);
}
