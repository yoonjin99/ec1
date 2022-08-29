package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.creator.ClaimDataCreatorInterface;
import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.OPT0003Type;
import com.plateer.ec1.common.code.order.OPT0004Type;
import com.plateer.ec1.common.code.order.OPT0005Type;
import com.plateer.ec1.common.model.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CancelDataCreatorImpl extends ClaimDataCreator implements ClaimDataCreatorInterface {

    public CancelDataCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.C;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("취소 update 데이터 생성 로직 호출");
        List<OpOrdBnfInfoModel> bnf = updateOrderBenefitData(vo);
        List<OpClmInfoModel> clm = updateOrderClaim(vo);

        ClaimProcessVo processVo = new ClaimProcessVo();
        processVo.setOpOrdBnfInfoModels(bnf);
        processVo.setOpClmInfoModels(clm);
        processVo.setClaimType(vo.getClaimType());
        return processVo;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("취소 insert 데이터 생성 로직 호출");
        List<OpClmInfoModel> clm = insertOrderClaim(vo);
        List<OpOrdBnfRelInfoModel> rel = insertOrderBenefitRelation(vo);
        List<OpOrdCostInfoModel> cost = insertOrderCost(vo);

        ClaimProcessVo processVo = new ClaimProcessVo();
        processVo.setOpClmInfoModels(clm);
        processVo.setOpOrdBnfRelInfoModels(rel);
        processVo.setOpOrdCostInfoModels(cost);
        processVo.setClaimType(vo.getClaimType());

        return processVo;
    }

    public List<OpOrdBnfInfoModel> updateOrderBenefitData(ClaimProcessVo vo) {
        return OpOrdBnfInfoModel.builder().build().updateOrderBenefitData(vo);
    }

    @Override
    public List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo) {
        // 취소수량
        List<OpClmInfoModel> opClmInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpClmInfoModels())){
            if(vo.getClaimType().equals(ClaimType.MCC.name())){
                for(OpClmInfoModel clm : vo.getOpClmInfoModels()){
                    clm.setOrdPrgsScd(OPT0004Type.CS.getType());
                    opClmInfoModelList.add(clm);
                }
            }else{
                for(OpClmInfoModel clm : vo.getOpClmInfoModels()){
                    clm.setCnclCnt(clm.getOrdCnt());
                    opClmInfoModelList.add(clm);
                }
            }
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo) {
        List<OpClmInfoModel> opClmInfoModelList = OpClmInfoModel.builder().build().insertOrderClaim(vo);
        if(!Objects.isNull(opClmInfoModelList)){
            for(OpClmInfoModel clm : vo.getOpClmInfoModels()){
                clm.setOrdClmTpCd(OPT0003Type.C.name());
                clm.setOrdPrgsScd(vo.getClaimType().equals(ClaimType.MCA.name()) ? OPT0004Type.CA.getType() : OPT0004Type.CS.getType());
                clm.setOrdClmCmtDtime(LocalDateTime.now());
            }
        }
        return opClmInfoModelList;
    }

    public List<OpOrdBnfRelInfoModel> insertOrderBenefitRelation(ClaimProcessVo vo) {
        return OpOrdBnfRelInfoModel.builder().build().insertOrderBenefitRelation(vo);
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
        // 적용구분코드, 클레임번호, 원주문비용번호
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdCostInfoModels())){
            for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                cost.setClmNo(vo.getClmNo());
                cost.setAplyCcd(OPT0005Type.CNCL.getType());
                cost.setOrgOrdCstNo(cost.getOrdCstNo());
                opOrdCostInfoModelList.add(cost);
            }
        }
        return opOrdCostInfoModelList;
    }

}
