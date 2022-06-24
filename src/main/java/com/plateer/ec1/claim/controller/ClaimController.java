package com.plateer.ec1.claim.controller;

import com.plateer.ec1.claim.service.impl.ClaimServiceImpl;
import com.plateer.ec1.claim.vo.ClaimVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/claim")
public class ClaimController {

    private final ClaimServiceImpl claimService;

    @PostMapping("/doClaim")
    public void claim(ClaimVo dto){
        claimService.claim(dto);
    }
}
