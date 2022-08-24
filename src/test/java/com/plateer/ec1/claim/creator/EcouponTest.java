package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.service.ClaimService;
import com.plateer.ec1.claim.vo.ClaimVo;
import com.plateer.ec1.claim.vo.OrdClaimInfoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EcouponTest {

    @Autowired
    ClaimService service;

    @Test
    @DisplayName("이쿠폰 취소 접수 테스트")
    void ecouponAcceptTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.MCA);
        vo.setOrdNo("O20220804103000");

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(1);

        OrdClaimInfoVo v2 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(2);

        OrdClaimInfoVo v3 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(3);

        claimInfoVos.add(v1);
        claimInfoVos.add(v2);
        claimInfoVos.add(v3);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }

    @Test
    @DisplayName("이쿠폰 취소 완료 테스트")
    void ecouponCompleteTest(){
        ClaimVo vo = new ClaimVo();
        vo.setClaimType(ClaimType.MCC);
        vo.setOrdNo("O20220804103000");

        List<OrdClaimInfoVo> claimInfoVos = new ArrayList<>();

        OrdClaimInfoVo v1 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(1);

        OrdClaimInfoVo v2 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(2);

        OrdClaimInfoVo v3 = new OrdClaimInfoVo();
        v1.setOrdNo("O20220804103000");
        v1.setOrdGoodsNo("P003");
        v1.setOrdItemNo("0");
        v1.setDvGrpNo(3);

        claimInfoVos.add(v1);
        claimInfoVos.add(v2);
        claimInfoVos.add(v3);

        vo.setOrdClaimInfoVoList(claimInfoVos);

        service.claim(vo);
    }
}
