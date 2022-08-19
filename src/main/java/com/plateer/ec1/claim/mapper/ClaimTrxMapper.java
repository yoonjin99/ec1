package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.common.model.order.OpOrdCostInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimTrxMapper {
    void insertOpClmInfo(List<OpClmInfoModel> opClmInfoModels);
    void insertOpOrdBnfRelInfo(List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModels);
    void insertOpOrdCostInfo(List<OpOrdCostInfoModel> opOrdCostInfoModels);
    void updateOpClmInfo(List<OpClmInfoModel> opClmInfoModels);
    void updateOpOrdBnfInfo(List<OpOrdBnfInfoModel> opOrdBnfInfoModels);
//    void updateOpOrdCostInfo(List<OpOrdCostInfoModel> opOrdCostInfoModels);
}
