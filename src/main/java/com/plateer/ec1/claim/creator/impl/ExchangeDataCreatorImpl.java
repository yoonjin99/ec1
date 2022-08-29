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
import com.plateer.ec1.common.code.order.OPT0014Type;
import com.plateer.ec1.common.model.order.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ExchangeDataCreatorImpl extends ClaimDataCreator implements ClaimDataCreatorInterface {

    public ExchangeDataCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
    }

    @Override
    public CreatorType getType() {
        return CreatorType.EX;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("교환 update 데이터 생성 로직 호출");
        List<OpClmInfoModel> clm = updateOrderClaim(vo);

        ClaimProcessVo processVo = new ClaimProcessVo();
        processVo.setOpClmInfoModels(clm);
        processVo.setClaimType(vo.getClaimType());
        return processVo;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("교환 update 데이터 생성 로직 호출");
        List<OpClmInfoModel> clm = insertOrderClaim(vo);
        List<OpOrdCostInfoModel> cost = insertOrderCost(vo);

        ClaimProcessVo processVo = new ClaimProcessVo();
        processVo.setOpClmInfoModels(clm);
        processVo.setOpOrdCostInfoModels(cost);
        processVo.setClaimType(vo.getClaimType());
        return processVo;
    }

    @Override
    public List<OpClmInfoModel> updateOrderClaim(ClaimProcessVo vo) {
        List<OpClmInfoModel> opClmInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpClmInfoModels())){
            vo.getOpClmInfoModels().forEach(clm -> {
                clm.setRtgsCnt(clm.getOrdCnt());
                opClmInfoModelList.add(clm);
            });
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpClmInfoModel> insertOrderClaim(ClaimProcessVo vo) {
        List<OpClmInfoModel> opClmInfoModelList = OpClmInfoModel.builder().build().insertOrderClaim(vo);
        if(!Objects.isNull(opClmInfoModelList)){
            for (OpClmInfoModel clm : opClmInfoModelList) {
                clm.setDvRvtCcd(OPT0014Type.RETURN.getType());
                clm.setOrdClmTpCd(OPT0003Type.X.name());
                clm.setOrdPrgsScd(OPT0004Type.EA.getType());
                clm.setDvGrpNo(clm.getDvGrpNo() + 1);
            }
            List<OpClmInfoModel> reOpClmInfoModels = new ArrayList<>(opClmInfoModelList);
            for (OpClmInfoModel clm : reOpClmInfoModels) {
                clm.setDvRvtCcd(OPT0014Type.DVP.getType());
                clm.setProcSeq(clm.getProcSeq() +  1);
                clm.setDvGrpNo(clm.getDvGrpNo() + 1);
                opClmInfoModelList.add(clm);
            }
        }
        return opClmInfoModelList;
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
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
