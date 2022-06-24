package com.plateer.ec1.claim.controller;

import com.plateer.ec1.claim.service.impl.ClaimServiceImpl;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimServiceImpl claimService;

    public void claim(ClaimVo dto){
        claimService.claim(dto);
    }
}
