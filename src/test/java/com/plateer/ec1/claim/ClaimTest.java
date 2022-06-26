package com.plateer.ec1.claim;

import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.service.ClaimService;
import com.plateer.ec1.claim.vo.ClaimVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClaimTest {

    @Autowired
    private ClaimService claimService;

    @Test
    void gccClaim(){
        ClaimVo dto = new ClaimVo();
        dto.setClaimType(ClaimType.GCC);
        claimService.claim(dto);
    }

    @Test
    void mcaClaim(){
        ClaimVo dto = new ClaimVo();
        dto.setClaimType(ClaimType.MCA);
        claimService.claim(dto);
    }
}
