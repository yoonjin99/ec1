package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.order.mapper.data.OrderDataTrxMapper;
import com.plateer.ec1.order.vo.OrderBenefitRelVo;
import com.plateer.ec1.order.vo.OrderVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderTransactionalService {

    private final OrderDataTrxMapper orderDataTrxMapper;

    @Transactional(rollbackFor={Exception.class, RuntimeException.class})
    public void insertOrder(OrderVo vo){
        try {
            log.info("--------------insertOrderData start");
            orderDataTrxMapper.insertOrderBase(vo.getOpOrdBaseModel());
            orderDataTrxMapper.insertOrderGoods(vo.getOpGoodsInfoList());
            orderDataTrxMapper.insertOrderClaim(vo.getOpClmInfoModelList());
            orderDataTrxMapper.insertOrderAreaInfo(vo.getOpDvpAreaInfo());
            orderDataTrxMapper.insertOrderDvpInfo(vo.getOpDvpInfoList());

            // TODO : 개선하기
            for(OpOrdBnfInfoModel bnf : vo.getOpOrdBnfInfoModelList()){
                String key = orderDataTrxMapper.insertOrderBnf(bnf);

                for(OrderBenefitRelVo rel : vo.getOpOrdBnfRelInfoModelList()){
                    if(bnf.getCpnIssNo().equals(rel.getCpnIssNo()) && bnf.getOrdNo().equals(rel.getOrdNo())){
                        rel.setOrdBnfNo(key);
                        orderDataTrxMapper.insertOrderBnfRel(rel);
                    }
                }
            }
            orderDataTrxMapper.insertOrderCost(vo.getOpOrdCostInfoModelList());
//            throw new Exception("데이터 등록 오류 발생");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
