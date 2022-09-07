package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.creator.ClaimDataCreatorInterface;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.*;
import com.plateer.ec1.common.model.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ReturnDataCreatorImpl extends ClaimDataCreator implements ClaimDataCreatorInterface {

    public ReturnDataCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
        this.claimMapper = claimMapper;
    }

    private final ClaimMapper claimMapper;

    @Override
    public CreatorType getType() {
        return CreatorType.R;
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
        List<OpClmInfoModel> opClmInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpClmInfoModels())){
            for (OpClmInfoModel clm : vo.getOpClmInfoModels()) {
                OpClmInfoModel clmInfoModel = clm.toBuilder()
                        .rtgsCnt(clm.getOrdCnt())
                        .build();
                opClmInfoModelList.add(clmInfoModel);
            }
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo) {
        List<OpClmInfoModel> opClmInfoModelList = OpClmInfoModel.builder().build().insertOrderClaim(vo);
        if(!Objects.isNull(opClmInfoModelList)){
            Integer dvpGrpNo = claimMapper.selectDvpGrpNo(vo.getOrdNo());
            for (OpClmInfoModel clm : opClmInfoModelList) {
                clm.setDvRvtCcd(OPT0014Type.RETURN.getType());
                clm.setOrdClmTpCd(OPT0003Type.R.name());
                clm.setOrdPrgsScd(OPT0004Type.RA.getType());
                clm.setDvGrpNo(dvpGrpNo + 1);
            }
        }
        return opClmInfoModelList;
    }

    public List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo) {
        return OpOrdBnfInfoModel.builder().build().updateOrderBenefitData(vo);
    }

    public List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo) {
        return OpOrdBnfRelInfoModel.builder().build().insertOrderBenefitRelation(vo);
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdCostInfoModels())){
            Integer dvpGrpNo = claimMapper.selectDvpGrpNo(vo.getOrdNo());
            for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                cost.setClmNo(vo.getClmNo());
                cost.setDvGrpNo(dvpGrpNo + 1);
                cost.setAplyCcd(OPT0005Type.APLY.getType());
                cost.setOrgOrdCstNo(cost.getOrdCstNo());
                cost.setDvAmtTpCd(OPT0006Type.RETURN.getType());
                if(vo.getImtnRsnCcd().equals(OPT0008Type.CONSUMER.getType())){
                    cost.setAplyDvAmt(3000L); // 고객사유일때
                }else{
                    cost.setDvBnfAmt(3000L); // 당사사유일때
                }
                cost.setOrgDvAmt(3000L);
                cost.setDvPlcTpCd(DVP0001Type.CHARGED.getType());
                cost.setImtnRsnCcd(vo.getImtnRsnCcd());
                opOrdCostInfoModelList.add(cost);
            }

            if(vo.getImtnRsnCcd().equals(OPT0008Type.COMPANY.getType())){
                for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                    OpOrdCostInfoModel reCost = cost.toBuilder()
                            .dvGrpNo(dvpGrpNo)
                            .aplyCcd(OPT0005Type.CNCL.getType())
                            .dvAmtTpCd(OPT0006Type.SHIPMENT.getType())
                            .orgOrdCstNo(cost.getOrdCstNo())
                            .orgDvAmt(cost.getAplyDvAmt())
                            .dvPlcTpCd(DVP0001Type.FREE.getType())
                            .build();
                    opOrdCostInfoModelList.add(reCost);
                }
            }
        }
        return opOrdCostInfoModelList;
    }
}
