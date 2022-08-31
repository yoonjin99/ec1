package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.creator.ClaimDataCreatorInterface;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.mapper.ClaimTrxMapper;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import com.plateer.ec1.common.code.order.*;
import com.plateer.ec1.common.model.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ExchangeDataCreatorImpl extends ClaimDataCreator implements ClaimDataCreatorInterface {

    public ExchangeDataCreatorImpl(ClaimMapper claimMapper, ClaimTrxMapper claimTrxMapper) {
        super(claimMapper, claimTrxMapper);
        this.claimMapper = claimMapper;
    }

    private final ClaimMapper claimMapper;

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
        List<OpDvpInfo> dvp = insertOpDvpInfo(clm);

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
            Integer dvpGrpNo = claimMapper.selectDvpGrpNo(vo.getOrdNo());
            for (OpClmInfoModel clm : opClmInfoModelList) {
                clm.setDvRvtCcd(OPT0014Type.RETURN.getType());
                clm.setOrdClmTpCd(OPT0003Type.X.name());
                clm.setOrdPrgsScd(OPT0004Type.EA.getType());
                clm.setDvGrpNo(dvpGrpNo + 1);
            }
            List<OpClmInfoModel> reOpClmInfoModels = new ArrayList<>(opClmInfoModelList);
            for (OpClmInfoModel clm : reOpClmInfoModels) {
                clm.setDvRvtCcd(OPT0014Type.DVP.getType());
                clm.setProcSeq(clm.getProcSeq() +  1);
                clm.setDvGrpNo(clm.getDvGrpNo() + 1);
                opClmInfoModelList.add(clm);
            }
        }
        log.info(opClmInfoModelList.toString() + "클레임 테이블값");
        return opClmInfoModelList;
    }

    @Override
    public List<OpOrdCostInfoModel> insertOrderCost(ClaimProcessVo vo) {
        List<OpOrdCostInfoModel> opOrdCostInfoModelList = new ArrayList<>();
        if(!Objects.isNull(vo.getOpOrdCostInfoModels())){
            for(OpOrdCostInfoModel cost : vo.getOpOrdCostInfoModels()){
                cost.setClmNo(vo.getClmNo());
                cost.setAplyCcd(OPT0005Type.APLY.getType());
                cost.setOrgOrdCstNo(cost.getOrdCstNo());
                cost.setDvAmtTpCd(OPT0006Type.EXCHANGE.getType());
                if(vo.getImtnRsnCcd().equals(OPT0008Type.CONSUMER.getType())){
                    cost.setAplyDvAmt(3000L); // 고객사유일때
                }else{
                    cost.setDvBnfAmt(3000L); // 당사사유일때
                }
                cost.setOrgDvAmt(3000L);
                cost.setDvPlcTpCd(DVP0001Type.CASH.getType());
                cost.setImtnRsnCcd(vo.getImtnRsnCcd());
                opOrdCostInfoModelList.add(cost);
            }
        }
        return opOrdCostInfoModelList;
    }

    private List<OpDvpInfo> insertOpDvpInfo(List<OpClmInfoModel> opClmInfoModelList){
        List<OpDvpInfo> list = new ArrayList<>();
        for(OpClmInfoModel clm : opClmInfoModelList){
            OpDvpInfo dvp = OpDvpInfo.builder()
                    .dvGrpNo(clm.getDvGrpNo())
                    .ordNo(clm.getOrdNo())
                    .dvpSeq(1)
                    .dvMthdCd(DVP0001Type.CHARGED.getType())
                    .build();
            list.add(dvp);
        }
        return list;
    }
}
