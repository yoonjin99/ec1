package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.service.ClaimService;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.claim.vo.OrdClaimInfoVo;
import com.plateer.ec1.common.code.order.OPT0008Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GeneralTest {

    @Autowired
    ClaimService service;

    @Test
    @DisplayName("전체 취소 데이터 생성 테스트 - 일반주문")
    void cancelTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.GCC);
        vo.setOrdNo("O20220808103004");
        vo.setCnclPrice(84950L);

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        OrdClaimInfoVo v2 = new OrdClaimInfoVo();
        v2.setOrdNo("O20220808103004");
        v2.setOrdGoodsNo("P002");
        v2.setOrdItemNo("1");
        v2.setDvGrpNo(2);

        claimInfoVos.add(v1);
        claimInfoVos.add(v2);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("부분 취소 데이터 생성 테스트")
    void partCancelTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.GCC);
        vo.setOrdNo("O20220808103004");

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("교환 테스트 - 고객 사유")
    void exchangeTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.EA);
        vo.setOrdNo("O20220808103004");
        vo.setImtnRsnCcd("10"); // 고객 사유

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("교환 테스트 - 당사 사유")
    void exchangeCompanyTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.EA);
        vo.setOrdNo("O20220808103004");
        vo.setImtnRsnCcd("20"); // 당사 사유

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("반품 접수 테스트 - 고객 사유")
    void ReturnConsumerTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.RA);
        vo.setOrdNo("O20220808103004");
        vo.setImtnRsnCcd("10"); // 고객 사유

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }
    
    @Test
    @DisplayName("반품 접수 테스트 - 당사 사유")
    void ReturnCompanyTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.RA);
        vo.setOrdNo("O20220808103004");
        vo.setImtnRsnCcd("20"); // 당사 사유

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("반품 철회 테스트")
    void ReturnWithdraw(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.RW);
        vo.setOrdNo("O20220808103004");

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220808103004");
        v1.setOrdGoodsNo("P001");
        v1.setOrdItemNo("1");
        v1.setDvGrpNo(1);

        claimInfoVos.add(v1);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

}
