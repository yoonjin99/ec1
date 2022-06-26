package com.plateer.ec1.claim.controller;

import com.plateer.ec1.claim.service.ClaimService;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/claim")
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("/doClaim")
    public void claim(ClaimVo dto){
        claimService.claim(dto);
    }
}
