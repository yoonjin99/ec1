package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.creator.ClaimDataCreatorInterface;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.OPT0003Type;
import com.plateer.ec1.common.code.order.OPT0004Type;
import com.plateer.ec1.common.code.order.OPT0005Type;
import com.plateer.ec1.common.model.order.OpClmInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfInfoModel;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfoModel;
import com.plateer.ec1.common.model.order.OpOrdCostInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class ReturnWithdrawCreatorImpl extends ClaimDataCreator implements ClaimDataCreatorInterface {

    public ReturnWithdrawCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.RW;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("반품 update 데이터 생성 로직 호출");
        List<OpOrdBnfInfoModel> bnf = updateOrderBenefitData(vo);
        List<OpClmInfoModel> clm = updateOrderClaim(vo);

        return ClaimProcessVo.builder()
                .opOrdBnfInfoModels(bnf)
                .opClmInfoModels(clm)
                .claimType(vo.getClaimType())
                .build();
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("반품 insert 데이터 생성 로직 호출");
        List<OpClmInfoModel> clm = insertOrderClaim(vo);
        List<OpOrdBnfRelInfoModel> rel = insertOrderBenefitRelation(vo);
        List<OpOrdCostInfoModel> cost = insertOrderCost(vo);

        return ClaimProcessVo.builder()
                .opClmInfoModels(clm)
                .opOrdBnfRelInfoModels(rel)
                .opOrdCostInfoModels(cost)
                .claimType(vo.getClaimType())
                .build();
    }

    @Override
    public List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo) {
        // 원주문 - 반품수량
        // 반품접수 - 반품수량
        List<OpClmInfoModel> opClmInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpClmInfoModels())){
            for (OpClmInfoModel clm : vo.getOpClmInfoModels()) {
                clm.setRtgsCnt(clm.getOrdCnt());
                clm.setCnclCnt(clm.getOrdCnt());
                opClmInfoModelList.add(clm);
            }
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo) {
        List<OpClmInfoModel> opClmInfoModelList = OpClmInfoModel.builder().build().insertOrderClaim(vo);
        if(!Objects.isNull(opClmInfoModelList)){
            for (OpClmInfoModel clm : opClmInfoModelList) {
                clm.setOrdClmTpCd(OPT0003Type.RC.name());
                clm.setOrdPrgsScd(OPT0004Type.RW.getType());
            }
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
        // 원주문비용번호만 추가해주면 됨
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdCostInfoModels())){
            for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                cost.setOrgOrdCstNo(cost.getOrdCstNo());
                opOrdCostInfoModelList.add(cost);
            }
        }
        return opOrdCostInfoModelList;
    }

    private List<OpOrdCostInfoModel> updateOrderCost(ClaimProcessVo vo){
        // 반품접수 - 취소배송비
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdCostInfoModels())){
            for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                cost.setCnclDvAmt(cost.getAplyDvAmt());
                opOrdCostInfoModelList.add(cost);
            }
        }
        return opOrdCostInfoModelList;
    }

    public List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo) {
        return OpOrdBnfInfoModel.builder().build().updateOrderBenefitData(vo);
    }

    public List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo) {
        return OpOrdBnfRelInfoModel.builder().build().insertOrderBenefitRelation(vo);
    }
}
